package com.ecycle.commodity.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.ecycle.commodity.constant.OrderStatus;
import lombok.Data;

/**
 * 订单
 * @TableName ecycle_order
 */
@TableName(value ="ecycle_order")
@Data
public class Order implements Serializable {
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
     * 出价 id
     */
    private UUID biddingId;

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
    private OrderStatus status;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 服务费支付时间
     */
    private Date serviceChargePayTime;

}