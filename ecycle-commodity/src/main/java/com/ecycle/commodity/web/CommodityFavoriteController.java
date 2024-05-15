package com.ecycle.commodity.web;

import com.ecycle.commodity.service.CommodityFavoriteService;
import com.ecycle.common.context.PageQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.context.RestResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/5/14
 * @Description 商品收藏接口
 */
@RestController
@Log4j2
@RequestMapping("/favorite")
public class CommodityFavoriteController {

    @Resource
    private CommodityFavoriteService commodityFavoriteService;

    @PostMapping("/save/{commodityId}")
    public RestResponse<Boolean> queryAll(@PathVariable(name = "commodityId") UUID commodityId) {
        try{
            commodityFavoriteService.favorite(commodityId);
        } catch (Exception e){
            log.error("收藏失败", e);
            RestResponse.validfail("未知异常");
        }
        return RestResponse.success(true);
    }

    @PutMapping("/cancel/{commodityId}")
    public RestResponse<Boolean> cancel(@PathVariable(name = "commodityId") UUID commodityId) {
        try{
            commodityFavoriteService.cancel(commodityId);
        } catch (Exception e){
            log.error("取消收藏失败", e);
            RestResponse.validfail("未知异常");
        }
        return RestResponse.success(true);
    }

    @PostMapping("/queryAll")
    public RestResponse<PageQueryResponse> queryAll(@RequestBody PageQueryRequest body) {
        PageQueryResponse result = commodityFavoriteService.queryAll(body);
        return RestResponse.success(result);
    }

    @GetMapping("/count")
    public RestResponse<Integer> count() {
        Integer result = commodityFavoriteService.queryCount();
        return RestResponse.success(result);
    }
}
