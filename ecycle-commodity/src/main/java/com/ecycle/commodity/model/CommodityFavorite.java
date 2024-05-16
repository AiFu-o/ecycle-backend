package com.ecycle.commodity.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 商品收藏
 * @author wangweichen
 * @TableName ecycle_commodity_favorite
 */
@TableName(value ="ecycle_commodity_favorite")
@Data
public class CommodityFavorite implements Serializable {
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
     * 收藏时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date favoriteTime;
}