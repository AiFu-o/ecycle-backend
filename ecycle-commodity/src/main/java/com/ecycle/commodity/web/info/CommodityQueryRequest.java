package com.ecycle.commodity.web.info;

import com.ecycle.commodity.constant.CommodityStatus;
import com.ecycle.common.context.PageQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author wangweichen
 * @Date 2024/4/12
 * @Description 商品列表查询传参
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommodityQueryRequest extends PageQueryRequest {
    private String input;

    private List<CommodityStatus> statusList;
}
