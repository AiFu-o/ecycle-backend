package com.ecycle.pay.mapper;

import com.ecycle.pay.constant.OrderTypeEnum;
import com.ecycle.pay.model.PayOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_pay_records(支付订单)】的数据库操作Mapper
* @createDate 2024-04-25 09:22:16
* @Entity com.ecycle.pay.model.PayRecords
*/
public interface PayOrderMapper extends BaseMapper<PayOrder> {

    /**
     * 根据订单 id 查询未支付订单
     * @param orderId 订单 id
     * @param orderType 订单类型
     * @return 支付订单
     */
    List<PayOrder> findNoPayOrderByOrderIdAndType(UUID orderId, OrderTypeEnum orderType);

    /**
     * 根据订单号获取支付订单
     * @param bilCode 订单号
     * @return 支付订单
     */
    PayOrder findByBillCode(String billCode);
}




