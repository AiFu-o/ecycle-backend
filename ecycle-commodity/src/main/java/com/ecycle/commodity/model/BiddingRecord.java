package com.ecycle.commodity.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ecycle.commodity.constant.BiddingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * 订单
 * @author wangweichen
 * @TableName ecycle_order
 */
@TableName(value ="ecycle_bidding_record")
@Data
public class BiddingRecord implements Serializable {
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
    private BiddingStatus status;

    /**
     * 商品价格
     */
    private BigDecimal commodityAmount;

    /**
     * 服务费应收
     */
    private BigDecimal serviceCharge;

    /**
     * 出价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}