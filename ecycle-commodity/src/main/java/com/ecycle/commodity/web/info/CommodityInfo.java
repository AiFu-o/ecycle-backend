package com.ecycle.commodity.web.info;

import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.model.UserAddress;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangweichen
 * @Date 2024/6/25
 * @Description TODO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommodityInfo extends Commodity {
    private UserAddress address;
    private String creatorName;
}
