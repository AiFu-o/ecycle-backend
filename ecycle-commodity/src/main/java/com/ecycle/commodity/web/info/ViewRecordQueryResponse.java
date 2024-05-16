package com.ecycle.commodity.web.info;

import com.ecycle.commodity.model.Commodity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author wangweichen
 * @Date 2024/5/13
 * @Description 商品浏览记录返回对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ViewRecordQueryResponse extends Commodity {
    private Date viewTime;
}
