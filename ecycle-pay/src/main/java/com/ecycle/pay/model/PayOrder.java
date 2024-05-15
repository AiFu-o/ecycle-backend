package com.ecycle.pay.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.baomidou.mybatisplus.annotation.Version;
import com.ecycle.pay.constant.OrderTypeEnum;
import com.ecycle.pay.constant.PaymentMethodEnum;
import com.ecycle.pay.constant.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 支付订单
 *
 * @author wangweichen
 * @TableName ecycle_pay_records
 */
@TableName(value = "ecycle_pay_order")
@Data
public class PayOrder implements Serializable {
    /**
     * id
     */
    @TableId
    private UUID id;

    @Version
    private Integer version;

    /**
     * 订单号
     */
    private String billCode;

    /**
     * 订单类型
     */
    private OrderTypeEnum orderType;

    /**
     * 订单 id
     */
    private UUID orderId;

    /**
     * 第三方平台交易流水号
     */
    private String transactionId;

    /**
     * 支付方式
     */
    private PaymentMethodEnum paymentMethod;

    /**
     * 支付金额
     */
    private Integer amount;

    /**
     * 退款金额
     */
    private Integer refundAmount;

    /**
     * 支付状态
     */
    private PaymentStatus status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;

    /**
     * 创建人
     */
    private UUID creatorId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}