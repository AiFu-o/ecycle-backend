package com.ecycle.auth.init;

import com.ecycle.auth.service.RoleService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wangweichen
 * @Date 2024/4/17
 * @Description auth初始化
 */
@Component
public class AuthDataInitializer {
    @Resource
    private RoleService roleService;

    @EventListener
    @Transactional(rollbackFor = Exception.class)
    public void init(ApplicationEvent event) {
        roleService.initRole();
    }
}
