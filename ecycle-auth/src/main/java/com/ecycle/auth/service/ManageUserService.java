package com.ecycle.auth.service;

import com.ecycle.auth.model.User;
import com.ecycle.auth.web.info.ManageUserQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author wangweichen
 */
public interface ManageUserService {

    /**
     * 查询管理员用户列表
     *
     * @param body 查询参数
     * @return 管理员用户列表
     */
    PageQueryResponse queryAll(ManageUserQueryRequest body);

    /**
     * 创建管理员用户
     *
     * @param user 用户信息
     * @return 用户 id
     */
    UUID create(User user);

    /**
     * 修改管理员用户信息
     *
     * @param user 用户信息
     * @return 用户 id
     */
    UUID update(User user);
}
