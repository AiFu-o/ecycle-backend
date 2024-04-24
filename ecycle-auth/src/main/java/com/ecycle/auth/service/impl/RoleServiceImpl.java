package com.ecycle.auth.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.auth.mapper.RoleMapper;
import com.ecycle.auth.model.Role;
import com.ecycle.auth.service.RoleService;
import com.ecycle.auth.service.UserInfoService;
import com.ecycle.auth.service.UserRoleService;
import com.ecycle.common.context.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_role】的数据库操作Service实现
 * @createDate 2024-04-01 10:15:14
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Value(value = "classpath*:/init/role.init.json")
    private org.springframework.core.io.Resource[] roleInitFiles;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private UserInfoService userInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initRole() {
        List<String> roleCodes = new ArrayList<>();
        JSONArray roles = new JSONArray();
        for (org.springframework.core.io.Resource roleInitFile : roleInitFiles) {
            String content = "";
            try {
                InputStream inputStream = roleInitFile.getInputStream();
                content = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException("json解析异常", e);
            }

            JSONArray initRoles = JSONArray.parseArray(content);
            for (Object initRoleObj : initRoles) {
                JSONObject initRole = (JSONObject) initRoleObj;
                if (!initRole.containsKey("name") ||
                        StringUtils.isEmpty(initRole.getString("name"))) {
                    throw new NullPointerException("name is not null");
                }
                if (!initRole.containsKey("code") ||
                        StringUtils.isEmpty(initRole.getString("code"))) {
                    throw new NullPointerException("code is not null");
                }
                roleCodes.add(initRole.getString("code"));
            }
            roles.addAll(initRoles);
        }

        List<String> existRoleCodes = baseMapper.hasRoleCodes(roleCodes);

        for (Object roleObj : roles) {
            JSONObject role = (JSONObject) roleObj;
            String code = role.getString("code");
            if (!existRoleCodes.contains(code)) {
                Role roleEntity = new Role();
                roleEntity.setId(UUID.randomUUID());
                roleEntity.setCode(code);
                roleEntity.setName(role.getString("name"));
                if (role.containsKey("desc")) {
                    roleEntity.setDesc(role.getString("desc"));
                }
                save(roleEntity);
            }
        }
    }

    @Override
    public List<Role> findRolesByUserId(UUID userId) {
        return userRoleService.findRolesByUserId(userId);
    }

    @Override
    public Role findByCode(String code) {
        return baseMapper.findByCode(code);
    }

    @Override
    public Boolean hasRole(String code) {
        UserInfo userInfo = userInfoService.getCurrentUserInfo();
        List<String> roles = userInfo.getRoles();
        return !roles.isEmpty() && roles.contains(code);
    }

}




