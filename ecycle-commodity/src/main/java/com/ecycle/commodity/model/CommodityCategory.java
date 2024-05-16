package com.ecycle.commodity.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.ecycle.commodity.constant.ServiceChargeType;
import com.fasterxml.jackson.annotation.JsonFormat;
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
     * 分类图标
     */
    private UUID iconFileId;

    /**
     * 是否启用
     */
    private Integer enable;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    /**
     * 服务费类型
     */
    private ServiceChargeType serviceChargeType;

    /**
     * 服务费设置
     */
    private BigDecimal serviceChargeSetting;

}