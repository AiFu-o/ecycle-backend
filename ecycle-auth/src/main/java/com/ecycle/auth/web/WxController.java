package com.ecycle.auth.web;

import com.ecycle.auth.service.WxService;
import com.ecycle.common.context.RestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author wangweichen
 * @Date 2024/4/3
 * @Description 微信接口
 */
@RestController
public class WxController {

    @Resource
    private WxService wxService;

    @GetMapping("/getAccessToken")
    public String getAccessToken() {
        return wxService.getAccessToken();
    }
}
