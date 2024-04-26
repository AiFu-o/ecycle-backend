package com.ecycle.pay.service;

import com.ecycle.pay.constant.OrderTypeEnum;
import com.ecycle.pay.model.PayOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_pay_records(支付流水)】的数据库操作Service
* @createDate 2024-04-25 09:22:16
*/
public interface PayOrderService extends IService<PayOrder> {

    /**
     * 判断订单状态
     * @param orderId 订单 id
     * @param orderType 订单类型
     * @return 支付订单
     */
    List<PayOrder> findNoPayOrderByOrderIdAndType(UUID orderId, OrderTypeEnum orderType);

    /**
     * 根据订单号获取支付订单
     * @param billCode 订单号
     * @return 支付订单
     */
    PayOrder findByBillCode(String billCode);
}
