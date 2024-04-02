package com.ecycle.gateway.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ecycle-auth")
public interface AuthFeignService {

    @GetMapping("/findByUsername")
    Object findByUsername(@RequestParam(name = "username") String username);
}
