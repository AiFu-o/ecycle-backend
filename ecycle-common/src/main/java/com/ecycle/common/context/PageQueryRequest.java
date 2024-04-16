package com.ecycle.common.context;

import lombok.Data;

import java.util.List;

/**
 * @author wangweichen
 * @Date 2024/4/12
 * @Description 分页查询请求参数
 */
@Data
public class PageQueryRequest {

    private Long pageSize;

    private Long pageIndex;

    private Boolean isPage;
}
