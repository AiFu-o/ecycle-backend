package com.ecycle.commodity.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.commodity.constant.BiddingStatus;
import com.ecycle.commodity.constant.CommodityStatus;
import com.ecycle.commodity.constant.ServiceChargeType;
import com.ecycle.commodity.exception.BiddingOrderException;
import com.ecycle.commodity.exception.CommodityException;
import com.ecycle.commodity.mapper.BiddingRecordMapper;
import com.ecycle.commodity.model.BiddingRecord;
import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.model.CommodityCategory;
import com.ecycle.commodity.service.*;
import com.ecycle.commodity.service.feign.NotificationFeignService;
import com.ecycle.commodity.web.info.BiddingRecordQueryRequest;
import com.ecycle.commodity.web.info.CreateBiddingRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.utils.CurrentUserInfoUtils;
import com.ecycle.common.utils.MybatisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_order(订单)】的数据库操作Service实现
 * @createDate 2024-04-19 08:42:25
 */
@Service
public class BiddingRecordServiceImpl extends ServiceImpl<BiddingRecordMapper, BiddingRecord>
        implements BiddingRecordService {

    @Resource
    private CommodityService commodityService;

    @Resource
    private CommodityCategoryService categoryService;

    @Resource
    private OrderService orderService;

    @Resource
    private NotificationService notificationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID create(CreateBiddingRequest request) {
        if (null == request.getCommodityId()) {
            throw new BiddingOrderException("商品不能为空");
        }
        if (null == request.getCommodityAmount()) {
            throw new BiddingOrderException("商品价格不能为空");
        }

        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        if (null == userId) {
            throw new BiddingOrderException("用户未登录");
        }

        Commodity commodity = commodityService.getById(request.getCommodityId());
        if (null == commodity) {
            throw new BiddingOrderException("找不到商品");
        }

        BigDecimal commodityAmount = request.getCommodityAmount();

        BigDecimal serviceCharge = mathServiceCharge(commodity, commodityAmount);
        BiddingRecord biddingRecord = new BiddingRecord();

        String billCode = generateBillCode();
        biddingRecord.setId(UUID.randomUUID());
        biddingRecord.setBillCode(billCode);
        biddingRecord.setStatus(BiddingStatus.BIDDING);
        biddingRecord.setCreatorId(userId);
        biddingRecord.setCommodityAmount(commodityAmount);
        biddingRecord.setServiceCharge(serviceCharge);
        biddingRecord.setCommodityId(commodity.getId());
        save(biddingRecord);

        // 发送消息提醒
        notificationService.quoteMessage(commodity.getId(), commodity.getCreatorId());

        // 更新最新价格
        commodity.setAmount(commodityAmount);
        commodityService.updateById(commodity);
        return biddingRecord.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCommodityAmount(UUID orderId, BigDecimal commodityAmount) {
        if (null == commodityAmount) {
            throw new BiddingOrderException("出价不能为空");
        }

        BiddingRecord biddingRecord = getById(orderId);
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        if (null == userId) {
            throw new BiddingOrderException("用户未登录");
        }
        if (!biddingRecord.getCreatorId().equals(userId)) {
            throw new BiddingOrderException("只能修改自己的出价");
        }
        Commodity commodity = commodityService.getById(biddingRecord.getCommodityId());

        BigDecimal serviceCharge = mathServiceCharge(commodity, commodityAmount);

        biddingRecord.setCommodityAmount(commodityAmount);
        biddingRecord.setServiceCharge(serviceCharge);

        updateById(biddingRecord);

        // 更新最新价格
        commodity.setAmount(commodityAmount);
        commodityService.updateById(commodity);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean sell(UUID orderId) {
        BiddingRecord biddingRecord = getById(orderId);
        BiddingStatus biddingOrderStatus = biddingRecord.getStatus();
        if (biddingOrderStatus != BiddingStatus.BIDDING) {
            throw new BiddingOrderException("竞价状态异常");
        }
        biddingRecord.setStatus(BiddingStatus.SUCCESS);
        updateById(biddingRecord);

        List<BiddingRecord> otherBindings = baseMapper.getOtherBiddingByCommodityId(biddingRecord.getCommodityId(), orderId);
        for (BiddingRecord otherBidding : otherBindings) {
            if (otherBidding.getStatus() != BiddingStatus.BIDDING) {
                throw new BiddingOrderException("订单状态异常");
            }
            // 其他的竞价设置为已关闭
            close(otherBidding);
        }
        orderService.generateOrder(biddingRecord);

        // 修改商品为已售出
        Commodity commodity = commodityService.getById(biddingRecord.getCommodityId());
        commodity.setStatus(CommodityStatus.SOLD);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean close(UUID orderId) {
        BiddingRecord order = getById(orderId);
        close(order);
        return true;
    }

    @Override
    public PageQueryResponse queryMineAll(BiddingRecordQueryRequest biddingRecordQueryRequest) {
        QueryChainWrapper<BiddingRecord> queryChainWrapper = super.query();

        queryChainWrapper.orderByAsc("create_time");

        MybatisUtils<BiddingRecord> mybatisUtils = new MybatisUtils<>();

        return mybatisUtils.pageQuery(queryChainWrapper, biddingRecordQueryRequest);
    }

    private void close(BiddingRecord order) {
        if (order.getStatus() == BiddingStatus.SUCCESS) {
            throw new BiddingOrderException("出价已经被确认, 请联系管理员");
        }
        if (order.getStatus() == BiddingStatus.CLOSED) {
            return;
        }
        order.setStatus(BiddingStatus.CLOSED);
        updateById(order);

        // 更新最新出价
        BigDecimal amount = baseMapper.getHighestAmount(order.getCommodityId());
        Commodity commodity = commodityService.getById(order.getCommodityId());
        commodity.setAmount(amount);
        commodityService.updateById(commodity);
    }

    private String generateBillCode() {
        Long timestampSeconds = Instant.now().getEpochSecond();
        // 生成随机数
        Random random = new Random();
        // 生成0到999的随机数
        int randomNumber = random.nextInt(1000);

        // 拼接账单代码
        return String.format("%s-%d-%03d", "SGD", timestampSeconds, randomNumber);
    }

    private BigDecimal mathServiceCharge(Commodity commodity, BigDecimal price) {
        CommodityCategory category = categoryService.getById(commodity.getCategoryId());
        if (null == category) {
            throw new CommodityException("找不到商品类型");
        }

        ServiceChargeType type = category.getServiceChargeType();

        if (null == type) {
            throw new CommodityException("找不到服务费类型");
        }

        BigDecimal serviceChargeSetting = category.getServiceChargeSetting();

        if (null == serviceChargeSetting || BigDecimal.ZERO.compareTo(serviceChargeSetting) >= 0) {
            throw new CommodityException("找不到服务费设置");
        }

        if (type == ServiceChargeType.RATIO_PAYMENT) {
            return price.multiply(serviceChargeSetting).setScale(2, RoundingMode.HALF_UP);
        } else {
            return serviceChargeSetting;
        }
    }

    public BiddingRecord getById(UUID id) {
        BiddingRecord biddingRecord = super.getById(id);
        if (null == biddingRecord) {
            throw new BiddingOrderException("找不到订单");
        }
        return biddingRecord;
    }

}




