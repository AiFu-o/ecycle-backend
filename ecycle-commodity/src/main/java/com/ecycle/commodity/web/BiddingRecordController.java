package com.ecycle.commodity.web;

import com.ecycle.commodity.exception.BiddingOrderException;
import com.ecycle.commodity.service.BiddingRecordService;
import com.ecycle.commodity.web.info.BiddingRecordQueryRequest;
import com.ecycle.commodity.web.info.CreateBiddingRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.context.RestResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/3/14
 * @Description 出价接口
 */
@RestController
@Log4j2
@RequestMapping("/bidding")
public class BiddingRecordController {

    @Resource
    private BiddingRecordService biddingRecordService;

    @PostMapping("/create")
    public RestResponse<UUID> createOrder(@RequestBody CreateBiddingRequest request) {
        try {
            return RestResponse.success(biddingRecordService.create(request));
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
            return RestResponse.success(biddingRecordService.close(orderId));
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
            return RestResponse.success(biddingRecordService.updateCommodityAmount(orderId, commodityAmount));
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
            return RestResponse.success(biddingRecordService.sell(orderId));
        } catch (BiddingOrderException e) {
            log.error(e.getMessage(), e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("未知异常", e);
            return RestResponse.validfail("未知异常");
        }
    }

    @PostMapping("/queryMineAll")
    public RestResponse<PageQueryResponse> queryMineAll(@RequestBody BiddingRecordQueryRequest biddingRecordQueryRequest) {
        try {
            return RestResponse.success(biddingRecordService.queryMineAll(biddingRecordQueryRequest));
        } catch (BiddingOrderException e) {
            log.error(e.getMessage(), e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("未知异常", e);
            return RestResponse.validfail("未知异常");
        }
    }

}
