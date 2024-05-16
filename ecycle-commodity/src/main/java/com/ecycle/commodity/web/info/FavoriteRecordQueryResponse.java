package com.ecycle.commodity.web.info;

import com.ecycle.commodity.model.Commodity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author wangweichen
 * @Date 2024/5/13
 * @Description 商品收藏记录返回对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FavoriteRecordQueryResponse extends Commodity {
    private Date createTime;
}
