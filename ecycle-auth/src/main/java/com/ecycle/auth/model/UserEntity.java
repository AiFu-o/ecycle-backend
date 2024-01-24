package com.ecycle.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangweichen
 * @Date 2024/1/24
 * @Description 用户实体
 */

@Data
@TableName("ecycle_user")
public class UserEntity implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    @IsKey
    @Column(comment = "id")
    private String id;

    @Column(comment = "用户名")
    @IsNotNull
    private String username;

    @Column(comment = "密码")
    @IsNotNull
    private String password;

    @Column(comment = "年龄")
    private int age;

    @Column(comment = "性别")
    private String sex;

    @Column(name = "create_time", comment = "创建时间")
    @ColumnType(MySqlTypeConstant.TIMESTAMP)
    @DefaultValue("CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    @Column(name = "modify_time", comment = "修改时间")
    @ColumnType(MySqlTypeConstant.TIMESTAMP)
    @IgnoreUpdate  @DefaultValue("NULL ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyTime;
}
