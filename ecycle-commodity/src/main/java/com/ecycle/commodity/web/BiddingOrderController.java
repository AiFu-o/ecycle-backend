package com.ecycle.commodity.web;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.ecycle.commodity.exception.BiddingOrderException;
import com.ecycle.commodity.exception.CommodityException;
import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.model.CommodityCategory;
import com.ecycle.commodity.service.BiddingOrderService;
import com.ecycle.commodity.service.CommodityCategoryService;
import com.ecycle.commodity.service.CommodityService;
import com.ecycle.commodity.web.info.CommodityQueryRequest;
import com.ecycle.commodity.web.info.CreateOrderRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.context.RestResponse;
import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/3/14
 * @Description 出价接口
 */
@RestController
@Log4j2
@RequestMapping("/bidding")
public class BiddingOrderController {

    @Resource
    private BiddingOrderService biddingOrderService;

    @PostMapping("/create")
    public RestResponse<UUID> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            return RestResponse.success(biddingOrderService.createOrder(request));
        } catch (BiddingOrderException e) {
            log.error(e.getMessage(), e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("未知异常", e);
            return RestResponse.validfail("未知异常");
        }

    }

    @PutMapping("/close/{orderId}")
    public RestResponse<Boolean> close(@PathVariable(name = "orderId") UUID orderId) {
        try {
            return RestResponse.success(biddingOrderService.close(orderId));
        } catch (BiddingOrderException e) {
            log.error(e.getMessage(), e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("未知异常", e);
            return RestResponse.validfail("未知异常");
        }

    }

    @PostMapping("/updateCommodityAmount/{orderId}")
    public RestResponse<Boolean> createOrder(@PathVariable(name = "orderId") UUID orderId,
                                             @RequestParam(name = "commodityAmount") BigDecimal commodityAmount) {
        try {
            return RestResponse.success(biddingOrderService.updateCommodityAmount(orderId, commodityAmount));
        } catch (BiddingOrderException e) {
            log.error(e.getMessage(), e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("未知异常", e);
            return RestResponse.validfail("未知异常");
        }

    }

    @PutMapping("/sell/{orderId}")
    public RestResponse<Boolean> createOrder(@PathVariable(name = "orderId") UUID orderId) {
        try {
            return RestResponse.success(biddingOrderService.sell(orderId));
        } catch (BiddingOrderException e) {
            log.error(e.getMessage(), e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("未知异常", e);
            return RestResponse.validfail("未知异常");
        }
    }

    @PutMapping("/payServiceCharge/{orderId}")
    public RestResponse<String> payServiceCharge(@PathVariable(name = "orderId") UUID orderId) {
        try {
            return biddingOrderService.payServiceCharge(orderId);
        } catch (BiddingOrderException e) {
            log.error(e.getMessage(), e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("未知异常", e);
            return RestResponse.validfail("未知异常");
        }
    }

    @PutMapping("/service-charge/success-callBack/{orderId}")
    public void serviceChargeSuccessCallBack(@PathVariable(name = "orderId") UUID orderId) {
        biddingOrderService.serviceChargeSuccess(orderId);
    }

}
