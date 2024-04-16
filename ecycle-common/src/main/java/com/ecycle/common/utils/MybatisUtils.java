package com.ecycle.common.utils;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecycle.common.context.PageQueryRequest;
import com.ecycle.common.context.PageQueryResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangweichen
 * @Date 2024/4/12
 * @Description TODO
 */
public class MybatisUtils<T> {

    public PageQueryResponse pageQuery(QueryChainWrapper<T> queryChainWrapper, PageQueryRequest request){
        PageQueryResponse result = new PageQueryResponse();
        Long total = 0L;

        List<T> resultData = new ArrayList<>();
        if (request.getIsPage()) {
            Page<T> page = new Page<>(request.getPageIndex(), request.getPageSize());
            page = queryChainWrapper.page(page);
            resultData = page.getRecords();
            total = page.getTotal();
        } else {
            resultData = queryChainWrapper.list();
            total = (long) resultData.size();
        }

        result.setTotal(total);
        result.setDataList(resultData);
        return result;
    }
}
