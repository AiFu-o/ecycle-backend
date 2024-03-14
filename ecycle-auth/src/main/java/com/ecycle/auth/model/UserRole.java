package com.ecycle.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangweichen
 * @Date 2024/1/24
 * @Description 用户角色
 */

@Data
@TableName("ecycle_user_role")
public class UserRole implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String roleId;

    private String userId;

}
