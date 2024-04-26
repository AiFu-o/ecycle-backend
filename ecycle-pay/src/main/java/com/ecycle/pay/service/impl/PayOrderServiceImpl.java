package com.ecycle.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.pay.constant.OrderTypeEnum;
import com.ecycle.pay.model.PayOrder;
import com.ecycle.pay.service.PayOrderService;
import com.ecycle.pay.mapper.PayOrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_pay_records(支付流水)】的数据库操作Service实现
 * @createDate 2024-04-25 09:22:16
 */
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder>
        implements PayOrderService {

    @Override
    public List<PayOrder> findNoPayOrderByOrderIdAndType(UUID orderId, OrderTypeEnum orderType) {
        return baseMapper.findNoPayOrderByOrderIdAndType(orderId, orderType);
    }

    @Override
    public PayOrder findByBillCode(String billCode) {
        return baseMapper.findByBillCode(billCode);
    }


}




