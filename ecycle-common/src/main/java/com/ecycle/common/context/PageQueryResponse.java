package com.ecycle.common.context;

import lombok.Data;

import java.util.List;

/**
 * @author wangweichen
 * @Date 2024/4/12
 * @Description 通用列表返回
 */
@Data
public class PageQueryResponse {

    private Long total;

    private List<?> dataList;
}
