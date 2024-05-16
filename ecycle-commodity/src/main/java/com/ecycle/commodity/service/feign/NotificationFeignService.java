package com.ecycle.commodity.service.feign;

import com.ecycle.commodity.web.info.CreateNotificationRequest;
import com.ecycle.common.context.RestResponse;
import com.ecycle.common.filter.HeaderInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wangweichen
 * @Date 2024/5/16
 * @Description 通知服务通信层
 */
@FeignClient(value = "ecycle-message", configuration = HeaderInterceptor.class)
public interface NotificationFeignService {

    /**
     * 发送消息通知
     * @param request 通知参数
     * @return 是否成功
     */
    @PostMapping("/send")
    RestResponse<Boolean> send(@RequestBody CreateNotificationRequest request);
}
