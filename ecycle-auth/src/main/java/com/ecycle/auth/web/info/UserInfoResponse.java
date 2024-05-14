package com.ecycle.auth.web.info;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author wangweichen
 * @Date 2024/5/8
 * @Description TODO
 */
@Data
public class UserInfoResponse {
    private String id;
    private String username;
    private String telephone;
    private String sex;
    private String age;
    private String profile;
    @TableField("nick_name")
    private String nickName;
    @TableField("create_time")
    private String createTime;
    @TableField("modify_time")
    private String modifyTime;
}
