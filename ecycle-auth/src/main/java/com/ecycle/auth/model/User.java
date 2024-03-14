package com.ecycle.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangweichen
 * @Date 2024/1/24
 * @Description 用户
 */

@Data
@TableName("ecycle_user")
public class User implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String username;

    private String password;

    private int age;

    private String sex;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;
}
