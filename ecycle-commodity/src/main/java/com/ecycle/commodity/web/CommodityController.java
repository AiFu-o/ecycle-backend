package com.ecycle.commodity.web;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.ecycle.commodity.exception.CommodityException;
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

    @GetMapping("/load/{id}")
    public RestResponse<Commodity> load(@PathVariable(name = "id") UUID id) {
        return RestResponse.success(commodityService.getById(id));
    }

    @PutMapping("/shelve/{id}")
    public RestResponse<Boolean> shelveProduct(@PathVariable(name = "id") UUID id) {
        try {
            commodityService.shelveProduct(id);
        } catch (CommodityException e) {
            return RestResponse.validfail(e.getMessage());
        }
        return RestResponse.success(true);
    }

    @PutMapping("/shelveOff/{id}")
    public RestResponse<Boolean> shelveOffProduct(@PathVariable(name = "id") UUID id) {
        try {
            commodityService.shelveOffProduct(id);
        } catch (CommodityException e) {
            return RestResponse.validfail(e.getMessage());
        }
        return RestResponse.success(true);
    }

    @PostMapping("/publish")
    public RestResponse<UUID> publish(@RequestBody Commodity commodity) {
        UUID id;
        try {
            id = commodityService.publish(commodity);
        } catch (CommodityException e) {
            return RestResponse.validfail(e.getMessage());
        }
        return RestResponse.success(id);
    }

    @PutMapping("/edit")
    public RestResponse<UUID> edit(@RequestBody Commodity commodity) {
        UUID id;
        try {
            id = commodityService.edit(commodity);
        } catch (CommodityException e) {
            return RestResponse.validfail(e.getMessage());
        }
        return RestResponse.success(id);
    }

    @PostMapping("/pageQueryAll")
    public RestResponse<PageQueryResponse> pageQueryAll(@RequestBody CommodityQueryRequest body) {
        PageQueryResponse result = commodityService.pageQueryAll(body);
        return RestResponse.success(result);
    }

    @PostMapping("/pageQueryMineAll")
    public RestResponse<PageQueryResponse> pageQueryMineAll(@RequestBody CommodityQueryRequest body) {
        PageQueryResponse result = commodityService.pageQueryMineAll(body);
        return RestResponse.success(result);
    }

    @GetMapping("/queryCommodityCategoryAll")
    public RestResponse<List<CommodityCategory>> queryCommodityCategoryAll() {
        QueryChainWrapper<CommodityCategory> query = categoryService.query();
        return RestResponse.success(query.list());
    }
}
