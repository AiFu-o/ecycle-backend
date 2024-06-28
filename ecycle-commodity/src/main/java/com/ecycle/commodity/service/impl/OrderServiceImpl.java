package com.ecycle.commodity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.commodity.constant.CommodityStatus;
import com.ecycle.commodity.exception.BiddingOrderException;
import com.ecycle.commodity.exception.OrderException;
import com.ecycle.commodity.model.BiddingRecord;
import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.service.CommodityService;
import com.ecycle.commodity.service.UserAddressService;
import com.ecycle.commodity.web.info.OrderInfo;
import com.ecycle.commodity.web.info.OrderQueryRequest;
import com.ecycle.commodity.constant.OrderStatus;
import com.ecycle.commodity.model.Order;
import com.ecycle.commodity.service.OrderService;
import com.ecycle.commodity.mapper.OrderMapper;
import com.ecycle.commodity.service.feign.PayFeignService;
import com.ecycle.commodity.web.info.OrderQueryResponse;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.context.RestResponse;
import com.ecycle.common.utils.CurrentUserInfoUtils;
import com.ecycle.common.utils.MybatisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_order(订单)】的数据库操作Service实现
* @createDate 2024-05-15 16:59:49
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    @Resource
    private UserAddressService userAddressService;

    @Resource
    private CommodityService commodityService;

    @Resource
    private PayFeignService payFeignService;

    @Override
    public void generateOrder(BiddingRecord biddingRecord) {
        Order order = new Order();

        order.setId(UUID.randomUUID());
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        order.setCommodityId(biddingRecord.getCommodityId());
        order.setBiddingId(biddingRecord.getId());
        order.setCreatorId(biddingRecord.getCreatorId());
        order.setCommodityAmount(biddingRecord.getCommodityAmount());
        order.setServiceChargeReceivable(biddingRecord.getServiceCharge());
        order.setBillCode(generateBillCode());
        save(order);
    }

    @Override
    public RestResponse<String> payServiceCharge(UUID orderId) {
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        if (null == userId) {
            throw new BiddingOrderException("订单异常");
        }
        Order order = getById(orderId);
        if (null == order) {
            throw new OrderException("找不到订单");
        }
        if (!userId.equals(order.getCreatorId())) {
            throw new OrderException("无法支付其他人的订单");
        }
        OrderStatus orderStatus = order.getStatus();
        if (!(orderStatus == OrderStatus.PENDING_PAYMENT ||
                orderStatus == OrderStatus.PAYMENT_ERROR)) {
            throw new OrderException("订单状态异常");
        }
        BigDecimal receivable = order.getServiceChargeReceivable();
        Integer amount = receivable.multiply(new BigDecimal("100")).intValue();
        return payFeignService.serviceChargePrePay(orderId, amount);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void serviceChargeSuccess(UUID orderId) {
        Order order = getById(orderId);
        order.setServiceChargeReceived(order.getServiceChargeReceivable());
        order.setStatus(OrderStatus.PENDING_VISIT);
        order.setServiceChargePayTime(new Date());
        updateById(order);
    }

    @Override
    public PageQueryResponse queryBySeller(OrderQueryRequest body) {
        PageQueryResponse result = new PageQueryResponse();
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        IPage<OrderQueryResponse> query = new Page<>(body.getPageIndex(), body.getPageSize());
        query = baseMapper.queryBySeller(query, body, userId);
        result.setTotal(query.getTotal());
        result.setDataList(query.getRecords());
        return result;
    }

    @Override
    public PageQueryResponse queryMineAll(OrderQueryRequest body) {
        PageQueryResponse result = new PageQueryResponse();
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        IPage<OrderQueryResponse> query = new Page<>(body.getPageIndex(), body.getPageSize());
        query = baseMapper.queryByBuyer(query, body, userId);
        result.setTotal(query.getTotal());
        result.setDataList(query.getRecords());
        return result;
    }

    @Override
    public OrderInfo loadInfo(UUID id) {
        OrderInfo info = (OrderInfo) baseMapper.selectById(id);
        Commodity commodity = commodityService.getById(info.getCommodityId());
        info.setCommodity(commodity);
        // 钱没付 或者退款了 就不给他看地址
        if(!(info.getStatus() == OrderStatus.PENDING_PAYMENT || info.getStatus() == OrderStatus.REFUNDED ||
                info.getStatus() == OrderStatus.PAYMENT_ERROR || info.getStatus() == OrderStatus.CLOSED)){
            info.setAddress(userAddressService.getById(commodity.getAddressId()));
        }
        return info;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finish(UUID orderId) {
        Order order = getById(orderId);
        if(order.getStatus() != OrderStatus.VISITED){
            throw new OrderException("订单状态异常");
        }

        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        if (null == userId || !userId.equals(order.getCreatorId())) {
            throw new OrderException("用户身份异常");
        }

        order.setStatus(OrderStatus.PENDING_REVIEW);
        order.setFinishTime(new Date());
        updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void arrived(UUID orderId) {
        Order order = getById(orderId);
        if(order.getStatus() != OrderStatus.PENDING_VISIT){
            throw new OrderException("订单状态异常");
        }

        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        if (null == userId || !userId.equals(order.getCreatorId())) {
            throw new OrderException("用户身份异常");
        }

        order.setStatus(OrderStatus.VISITED);
        order.setArrivedTime(new Date());
        updateById(order);
    }

    private String generateBillCode() {
        Long timestampSeconds = Instant.now().getEpochSecond();
        // 生成随机数
        Random random = new Random();
        // 生成0到999的随机数
        int randomNumber = random.nextInt(1000);

        // 拼接账单代码
        return String.format("%s-%d-%03d", "OD", timestampSeconds, randomNumber);
    }

}




