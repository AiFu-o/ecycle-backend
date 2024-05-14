package com.ecycle.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/1/24
 * @Description 用户
 */

@Data
@TableName("ecycle_user")
public class User implements Serializable {

    /**
     * id
     */
    @TableId
    private UUID id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户名
     */
    private String username;

    /**
     * openId
     */
    private String openId;

    /**
     * 头像
     */
    private String profile;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String sex;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}
