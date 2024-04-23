package com.ecycle.commodity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.commodity.constant.BiddingOrderStatus;
import com.ecycle.commodity.constant.ServiceChargeType;
import com.ecycle.commodity.exception.BiddingOrderException;
import com.ecycle.commodity.exception.CommodityException;
import com.ecycle.commodity.mapper.BiddingOrderMapper;
import com.ecycle.commodity.model.BiddingOrder;
import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.model.CommodityCategory;
import com.ecycle.commodity.service.BiddingOrderService;
import com.ecycle.commodity.service.CommodityCategoryService;
import com.ecycle.commodity.service.CommodityService;
import com.ecycle.commodity.web.info.CreateOrderRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_order(订单)】的数据库操作Service实现
 * @createDate 2024-04-19 08:42:25
 */
@Service
public class BiddingOrderServiceImpl extends ServiceImpl<BiddingOrderMapper, BiddingOrder>
        implements BiddingOrderService {

    @Resource
    private CommodityService commodityService;

    @Resource
    private CommodityCategoryService categoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID createOrder(CreateOrderRequest request) {
        if (null == request.getCommodityId()) {
            throw new BiddingOrderException("商品不能为空");
        }
        if (null == request.getCommodityAmount()) {
            throw new BiddingOrderException("商品价格不能为空");
        }

        UUID userId = JwtTokenUtils.getCurrentUserId();
        if(null == userId){
            throw new BiddingOrderException("用户未登录");
        }

        Commodity commodity = commodityService.getById(request.getCommodityId());
        if (null == commodity) {
            throw new BiddingOrderException("找不到商品");
        }

        BigDecimal commodityAmount = request.getCommodityAmount();

        BigDecimal serviceCharge = mathServiceCharge(commodity, commodityAmount);
        BiddingOrder biddingOrder = new BiddingOrder();

        String billCode = generateBillCode();
        biddingOrder.setId(UUID.randomUUID());
        biddingOrder.setBillCode(billCode);
        biddingOrder.setStatus(BiddingOrderStatus.BIDDING);
        biddingOrder.setCreatorId(userId);
        biddingOrder.setCommodityAmount(commodityAmount);
        biddingOrder.setServiceChargeReceivable(serviceCharge);
        biddingOrder.setCommodityId(commodity.getId());
        save(biddingOrder);
        return biddingOrder.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCommodityAmount(UUID orderId, BigDecimal commodityAmount) {
        if (null == commodityAmount) {
            throw new BiddingOrderException("出价不能为空");
        }

        BiddingOrder biddingOrder = getById(orderId);
        UUID userId = JwtTokenUtils.getCurrentUserId();
        if(null == userId){
            throw new BiddingOrderException("用户未登录");
        }
        if(biddingOrder.getCreatorId() != userId){
            throw new BiddingOrderException("只能修改自己的出价");
        }
        Commodity commodity = commodityService.getById(biddingOrder.getCommodityId());

        BigDecimal serviceCharge = mathServiceCharge(commodity, commodityAmount);

        biddingOrder.setCommodityAmount(commodityAmount);
        biddingOrder.setServiceChargeReceivable(serviceCharge);

        updateById(biddingOrder);
        return true;
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

    private BigDecimal mathServiceCharge(Commodity commodity, BigDecimal price){
        CommodityCategory category = categoryService.getById(commodity.getCategoryId());
        if(null == category){
            throw new CommodityException("找不到商品类型");
        }

        ServiceChargeType type = category.getServiceChargeType();

        if(null == type){
            throw new CommodityException("找不到服务费类型");
        }

        BigDecimal serviceChargeSetting = category.getServiceChargeSetting();

        if(null == serviceChargeSetting || BigDecimal.ZERO.compareTo(serviceChargeSetting) <= 0){
            throw new CommodityException("找不到服务费设置");
        }

        if(type == ServiceChargeType.RATIO_PAYMENT){
            return price.multiply(serviceChargeSetting).setScale(2, RoundingMode.HALF_UP);
        } else {
            return serviceChargeSetting;
        }
    }

}




