package com.ecycle.pay.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import com.ecycle.common.filter.HeaderInterceptor;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/4/26
 * @Description TODO
 */
@FeignClient(value = "ecycle-commodity", configuration = HeaderInterceptor.class)
public interface OrderFeign {

    /**
     * 服务费支付成功
     * @param orderId 订单 id
     */
    @PutMapping("/order/service-charge/success-callBack/{orderId}")
    void serviceChargeSuccessCallBack(@PathVariable(name = "orderId") UUID orderId);

}
