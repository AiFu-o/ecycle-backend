package com.ecycle.commodity.web;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.ecycle.commodity.exception.CommodityCategoryException;
import com.ecycle.commodity.model.CommodityCategory;
import com.ecycle.commodity.service.CommodityCategoryService;
import com.ecycle.common.context.PageQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.context.RestResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/5/14
 * @Description 商品分类接口
 */
@RestController
@Log4j2
@RequestMapping("/commodity-category")
public class CommodityCategoryController {

    @Resource
    private CommodityCategoryService categoryService;

    @GetMapping("/queryAll")
    public RestResponse<List<CommodityCategory>> queryAll() {
        QueryChainWrapper<CommodityCategory> query = categoryService.query();
        return RestResponse.success(query.list());
    }

    @PostMapping("/pageQueryAll")
    public RestResponse<PageQueryResponse> pageQueryAll(@RequestBody PageQueryRequest body) {
        try {
            PageQueryResponse result = categoryService.queryAll(body);
            return RestResponse.success(result);
        } catch (Exception e) {
            log.error("获取商品分类失败", e);
            return RestResponse.validfail("未知异常");
        }
    }

    @PostMapping("/save")
    public RestResponse<UUID> save(@RequestBody CommodityCategory category) {
        try {
            UUID id = categoryService.doSave(category);
            return RestResponse.success(id);
        } catch (CommodityCategoryException e) {
            log.error("保存失败", e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("保存失败", e);
            return RestResponse.validfail("未知异常");
        }
    }

    @GetMapping("/load/{id}")
    public RestResponse<CommodityCategory> load(@PathVariable(name = "id") UUID id) {
        try {
            return RestResponse.success(categoryService.getById(id));
        } catch (Exception e) {
            log.error("获取商品分类失败", e);
            return RestResponse.validfail("未知异常");
        }
    }


    @PutMapping("/save")
    public RestResponse<UUID> update(@RequestBody CommodityCategory category) {
        try {
            UUID id = categoryService.edit(category);
            return RestResponse.success(id);
        } catch (CommodityCategoryException e) {
            log.error("保存失败", e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("保存失败", e);
            return RestResponse.validfail("未知异常");
        }
    }

}
