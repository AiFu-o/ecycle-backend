package com.ecycle.commodity.web;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.model.CommodityCategory;
import com.ecycle.commodity.service.CommodityCategoryService;
import com.ecycle.commodity.service.CommodityService;
import com.ecycle.commodity.web.info.CommodityQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.context.RestResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/3/14
 * @Description 商品接口
 */
@RestController
@Log4j2
@RequestMapping("/commodity")
public class CommodityController {

    @Resource
    private CommodityService commodityService;

    @Resource
    private CommodityCategoryService categoryService;

    @PostMapping("/save")
    public RestResponse<String> create(@RequestBody Commodity commodity) {
        if (null == commodity.getId()) {
            commodity.setId(UUID.randomUUID().toString());
        }
        commodityService.save(commodity);
        return RestResponse.success(commodity.getId());
    }

    @PutMapping("/save")
    public RestResponse<String> update(@RequestBody Commodity body) {
        commodityService.updateById(body);
        return RestResponse.success(body.getId());
    }

    @PostMapping("/pageQueryAll")
    public RestResponse<PageQueryResponse> pageQueryAll(@RequestBody CommodityQueryRequest body) {
        PageQueryResponse result = commodityService.pageQueryAll(body);
        return RestResponse.success(result);
    }

    @GetMapping("/queryCommodityCategoryAll")
    public RestResponse<List<CommodityCategory>> queryCommodityCategoryAll() {
        QueryChainWrapper<CommodityCategory> query = categoryService.query();
        return RestResponse.success(query.list());
    }
}
