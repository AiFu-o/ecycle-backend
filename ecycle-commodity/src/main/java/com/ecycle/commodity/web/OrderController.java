package com.ecycle.commodity.web;

import com.ecycle.commodity.web.info.OrderQueryRequest;
import com.ecycle.commodity.exception.BiddingOrderException;
import com.ecycle.commodity.exception.OrderException;
import com.ecycle.commodity.service.OrderService;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.context.RestResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/3/14
 * @Description 订单接口
 */
@RestController
@Log4j2
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PutMapping("/payServiceCharge/{orderId}")
    public RestResponse<String> payServiceCharge(@PathVariable(name = "orderId") UUID orderId) {
        try {
            return orderService.payServiceCharge(orderId);
        } catch (OrderException e) {
            log.error(e.getMessage(), e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("未知异常", e);
            return RestResponse.validfail("未知异常");
        }
    }

    @PutMapping("/service-charge/success-callBack/{orderId}")
    public void serviceChargeSuccessCallBack(@PathVariable(name = "orderId") UUID orderId) {
        orderService.serviceChargeSuccess(orderId);
    }

    @PostMapping("/queryMineAll")
    public RestResponse<PageQueryResponse> queryMineAll(@RequestBody OrderQueryRequest orderQueryRequest) {
        try {
            return RestResponse.success(orderService.queryMineAll(orderQueryRequest));
        } catch (BiddingOrderException e) {
            log.error(e.getMessage(), e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("未知异常", e);
            return RestResponse.validfail("未知异常");
        }
    }

}
