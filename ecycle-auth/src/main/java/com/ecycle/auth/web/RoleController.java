package com.ecycle.auth.web;

import com.ecycle.auth.service.RoleService;
import com.ecycle.auth.service.UserService;
import com.ecycle.common.context.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangweichen
 * @Date 2024/3/14
 * @Description 用户接口
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/hasRole")
    public RestResponse<Boolean> findByUsername(@RequestParam(name = "roleCode") String roleCode) {
        return RestResponse.success(roleService.hasRole(roleCode));
    }

}
