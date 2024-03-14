package com.ecycle.commodity.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "ecycle-auth")
public interface FeignTestService {
    @GetMapping("/user/queryAll")
    Object queryAll();
}
