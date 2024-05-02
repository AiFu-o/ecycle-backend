package com.ecycle.commodity.service.feign;

import com.ecycle.common.context.RestResponse;
import com.ecycle.common.filter.HeaderInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * @author wangweichen
 * 支付服务访问层
 */
@FeignClient(value = "ecycle-pay", configuration = HeaderInterceptor.class)
public interface PayFeignService {

    /**
     * 生成服务费预付单
     *
     * @param orderId 出价订单 id
     * @param amount 价格
     * @return 预付单需要的回调
     */
    @PostMapping("/wx-pay/service-charge/prepay")
    RestResponse<String> serviceChargePrePay(@RequestParam("orderId") UUID orderId,
                                                  @RequestParam("amount") Integer amount);
}
