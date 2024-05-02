package com.ecycle.commodity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.ecycle.commodity.constant.CommodityStatus;
import lombok.Data;

/**
 * 商品
 *
 * @author wangweichen
 * @TableName ecycle_commodity
 */
@TableName(value = "ecycle_commodity")
@Data
public class Commodity implements Serializable {
    /**
     * id
     */
    @TableId
    private UUID id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品信息
     */
    private String info;

    /**
     * 商品种类
     */
    private UUID categoryId;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date modifyTime;

    /**
     * 创建人id
     */
    private UUID creatorId;

    /**
     * 附件 id
     */
    private UUID belongId;

    /**
     * 浏览量
     */
    private Integer pageViews;

    /**
     * 商品状态
     */
    private CommodityStatus status;

    /**
     * 成交价
     */
    private BigDecimal amount;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 服务费
     */
    private BigDecimal serviceCharge;

    /**
     * 地址
     */
    private UUID addressId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}