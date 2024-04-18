package com.ecycle.commodity.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wangweichen
 * auth 服务访问层
 */
@FeignClient(value = "ecycle-auth")
public interface AuthFeignService {

    /**
     * 判断是不是有此角色
     * @param roleCode 角色编号
     * @return 是否有此角色
     */
    @GetMapping("/role/hasRole")
    Boolean hasRole(@RequestParam(name = "roleCode") String roleCode);
}
