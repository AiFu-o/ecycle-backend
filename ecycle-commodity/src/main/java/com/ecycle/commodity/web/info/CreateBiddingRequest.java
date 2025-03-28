package com.ecycle.commodity.web.info;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/4/19
 * @Description 创建出价传参
 */
@Data
public class CreateBiddingRequest {

    /**
     * 商品 id
     */
    private UUID commodityId;

    /**
     * 商品价格
     */
    private BigDecimal commodityAmount;

}
