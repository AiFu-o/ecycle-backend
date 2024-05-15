package com.ecycle.commodity.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 商品浏览记录
 * @author wangweichen
 * @TableName ecycle_commodity_view_record
 */
@TableName(value ="ecycle_commodity_view_record")
@Data
public class CommodityViewRecord implements Serializable {
    /**
     * id
     */
    @TableId
    private UUID id;

    /**
     * 用户 id
     */
    private UUID userId;

    /**
     * 商品 id
     */
    private UUID commodityId;

    /**
     * 浏览时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date viewTime;
}