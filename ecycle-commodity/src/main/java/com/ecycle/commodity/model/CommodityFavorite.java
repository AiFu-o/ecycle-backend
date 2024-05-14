package com.ecycle.commodity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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
    private String id;

    /**
     * 用户 id
     */
    private String userId;

    /**
     * 商品 id
     */
    private String commodityId;

    /**
     * 收藏时间
     */
    private Date favoriteTime;
}