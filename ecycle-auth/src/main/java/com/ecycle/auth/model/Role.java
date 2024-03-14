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
 * @Description 角色
 */
@Data
@TableName("ecycle_role")
public class Role implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String code;

    private String name;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

}
