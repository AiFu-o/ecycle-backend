package com.ecycle.auth.web;

import com.ecycle.auth.service.ManageUserService;
import com.ecycle.auth.service.UserService;
import com.ecycle.auth.web.info.ManageUserQueryRequest;
import com.ecycle.auth.web.info.UserInfoResponse;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.context.RestResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/3/14
 * @Description 用户接口
 */
@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private ManageUserService manageUserService;

    @GetMapping("/findByUsername")
    public Object findByUsername(@RequestParam(name = "username") String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/find/{id}")
    public RestResponse<UserInfoResponse> findByUsername(@PathVariable(name = "id") UUID id) {
        try{
            return RestResponse.success(userService.findInfoById(id));
        } catch (Exception e){
            log.error("未知异常",e);
            return RestResponse.validfail("未知异常");
        }
    }

    @PostMapping("/query-manage-user")
    public RestResponse<PageQueryResponse> queryManageUser(@RequestBody ManageUserQueryRequest body) {
        try{
            return RestResponse.success(manageUserService.queryAll(body));
        } catch (Exception e){
            log.error("未知异常",e);
            return RestResponse.validfail("未知异常");
        }
    }
}
