package com.ecycle.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.pay.constant.OrderTypeEnum;
import com.ecycle.pay.exception.PayRecordException;
import com.ecycle.pay.model.PayRecord;
import com.ecycle.pay.service.PayRecordService;
import com.ecycle.pay.mapper.PayRecordMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_pay_records(支付流水)】的数据库操作Service实现
* @createDate 2024-04-25 09:22:16
*/
@Service
public class PayRecordServiceImpl extends ServiceImpl<PayRecordMapper, PayRecord>
    implements PayRecordService {

    private void createOrder(OrderTypeEnum orderType, UUID orderId, Long amount){
        if(null == orderType){
            throw new PayRecordException("支付类型不能为空");
        }
        PayRecord payRecord = new PayRecord();
        payRecord.setOrderId(orderId);
        payRecord.setOrderId(orderId);
    }

}




