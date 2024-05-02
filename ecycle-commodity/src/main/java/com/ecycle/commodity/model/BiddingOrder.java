package com.ecycle.commodity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ecycle.commodity.constant.BiddingOrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * 订单
 * @TableName ecycle_order
 */
@TableName(value ="ecycle_bidding_order")
@Data
public class BiddingOrder implements Serializable {
    /**
     * id
     */
    @TableId
    private UUID id;

    /**
     * 订单编号
     */
    private String billCode;

    /**
     * 商品 id
     */
    private UUID commodityId;

    /**
     * 创建人 id
     */
    private UUID creatorId;

    /**
     * 状态
     */
    private BiddingOrderStatus status;

    /**
     * 商品价格
     */
    private BigDecimal commodityAmount;

    /**
     * 服务费应收
     */
    private BigDecimal serviceChargeReceivable;

    /**
     * 服务费实收
     */
    private BigDecimal serviceChargeReceived;

    /**
     * 出价时间
     */
    private Date createTime;

    /**
     * 确定出价时间
     */
    private Date confirmTime;

    /**
     * 服务费支付时间
     */
    private Date serviceChargePayTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}