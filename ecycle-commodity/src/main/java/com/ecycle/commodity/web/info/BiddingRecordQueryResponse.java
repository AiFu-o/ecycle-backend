package com.ecycle.commodity.web.info;

import com.ecycle.commodity.model.BiddingRecord;
import com.ecycle.common.context.PageQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/5/15
 * @Description TODO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BiddingRecordQueryResponse extends BiddingRecord {
    /**
     * 封面 id
     */
    UUID coverId;
    /**
     * 商品名称
     */
    String commodityName;
    /**
     * 商品描述
     */
    String commodityInfo;
}
