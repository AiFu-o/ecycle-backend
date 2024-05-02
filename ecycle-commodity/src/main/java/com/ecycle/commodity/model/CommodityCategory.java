package com.ecycle.commodity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.ecycle.commodity.constant.ServiceChargeType;
import lombok.Data;

/**
 * 商品分类
 * @author wangweichen
 * @TableName ecycle_commodity_category
 */
@TableName(value ="ecycle_commodity_category")
@Data
public class CommodityCategory implements Serializable {
    /**
     * id
     */
    @TableId
    private UUID id;

    /**
     * 名称
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 是否启用
     */
    private Integer enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 服务费类型
     */
    private ServiceChargeType serviceChargeType;

    /**
     * 服务费设置
     */
    private BigDecimal serviceChargeSetting;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}