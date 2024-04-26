package com.ecycle.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.pay.model.PayRefundOrder;
import com.ecycle.pay.service.PayRefundOrderService;
import com.ecycle.pay.mapper.PayRefundOrderMapper;
import org.springframework.stereotype.Service;

/**
* @author wangweichen
* @description 针对表【ecycle_pay_refund_records(支付退款流水)】的数据库操作Service实现
* @createDate 2024-04-25 09:22:16
*/
@Service
public class PayRefundOrderServiceImpl extends ServiceImpl<PayRefundOrderMapper, PayRefundOrder>
    implements PayRefundOrderService {

}




