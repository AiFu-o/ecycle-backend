package com.ecycle.pay.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 支付退款订单
 *
 * @TableName ecycle_pay_refund_records
 */
@TableName(value = "ecycle_pay_refund_order")
@Data
public class PayRefundOrder implements Serializable {
    /**
     * id
     */
    @TableId
    private String id;

    /**
     * 第三方平台退款交易流水号
     */
    private Integer transactionId;

    /**
     * 退款单编号
     */
    private Integer billCode;

    /**
     * 退款状态
     */
    private String status;

    /**
     * 退款金额
     */
    private Integer amount;

    /**
     * 支付订单 id
     */
    private String payOrderId;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 到账时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date refundTime;

    /**
     * 创建人 id
     */
    private UUID creatorId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}