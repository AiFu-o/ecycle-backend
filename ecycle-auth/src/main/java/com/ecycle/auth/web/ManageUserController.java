package com.ecycle.auth.web;

import com.ecycle.auth.exception.UserException;
import com.ecycle.auth.model.User;
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
 * @Description 管理员用户接口
 */
@RestController
@RequestMapping("/manage-user")
@Log4j2
public class ManageUserController {

    @Resource
    private ManageUserService manageUserService;


    @PostMapping("/queryAll")
    public RestResponse<PageQueryResponse> queryManageUser(@RequestBody ManageUserQueryRequest body) {
        try {
            return RestResponse.success(manageUserService.queryAll(body));
        } catch (Exception e) {
            log.error("未知异常", e);
            return RestResponse.validfail("未知异常");
        }
    }

    @PostMapping("/create")
    public RestResponse<UUID> create(@RequestBody User body) {
        try {
            return RestResponse.success(manageUserService.create(body));
        } catch (UserException e) {
            log.error(e.getMessage(), e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("未知异常", e);
            return RestResponse.validfail("未知异常");
        }
    }

    @PutMapping("/update")
    public RestResponse<UUID> update(@RequestBody User body) {
        try {
            return RestResponse.success(manageUserService.update(body));
        } catch (UserException e) {
            log.error(e.getMessage(), e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("未知异常", e);
            return RestResponse.validfail("未知异常");
        }
    }
}
