package com.ecycle.pay.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/4/26
 * @Description TODO
 */
@FeignClient(value = "ecycle-bidding")
public interface BiddingFeign {

    /**
     * 服务费支付成功
     * @param orderId 订单 id
     */
    @PutMapping("/bidding/service-charge/success-callBack/{orderId}")
    void serviceChargeSuccessCallBack(@PathVariable(name = "orderId") UUID orderId);

}
