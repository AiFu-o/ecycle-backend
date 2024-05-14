package com.ecycle.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecycle.auth.exception.UserException;
import com.ecycle.auth.mapper.UserMapper;
import com.ecycle.auth.model.User;
import com.ecycle.auth.service.ManageUserService;
import com.ecycle.auth.service.UserService;
import com.ecycle.auth.web.info.ManageUserQueryRequest;
import com.ecycle.auth.web.info.UserInfoResponse;
import com.ecycle.common.context.PageQueryResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/5/8
 * @Description 管理员用户类
 */
@Service
public class ManageUserServiceImpl implements ManageUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public PageQueryResponse queryAll(ManageUserQueryRequest body) {
        PageQueryResponse result = new PageQueryResponse();
        IPage<UserInfoResponse> query = new Page<>(body.getPageIndex(), body.getPageSize());
        query = userMapper.queryManageUser(query, body);
        result.setTotal(query.getTotal());
        result.setDataList(query.getRecords());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID create(User user) {
        UUID id;
        if (null == user.getId()) {
            id = UUID.randomUUID();
        } else {
            id = user.getId();

            if (StringUtils.isEmpty(user.getPassword())) {
                throw new UserException("密码不能为空");
            }
            if (StringUtils.isEmpty(user.getUsername())) {
                throw new UserException("用户名不能为空");
            }
            if (StringUtils.isEmpty(user.getTelephone())) {
                throw new UserException("手机号不能为空");
            }

            user.setId(id);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
            userService.addRoleByCode("admin", user);
        }
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID update(User user) {
        if (StringUtils.isEmpty(user.getTelephone())) {
            throw new UserException("手机号不能为空");
        }
        User oldUser = userMapper.selectById(user.getId());
        if (null == oldUser) {
            throw new UserException("找不到用户");
        }
        oldUser.setUsername(user.getUsername());

        if (StringUtils.isNotEmpty(user.getSex())) {
            oldUser.setSex(user.getSex());
        }
        if (null != user.getAge()) {
            oldUser.setAge(user.getAge());
        }
        return oldUser.getId();
    }

}
