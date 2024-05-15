package com.ecycle.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.ecycle.auth.constant.ProviderApplyStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 回收商申请单
 * @author wangweichen
 * @TableName ecycle_provider_apply
 */
@TableName(value ="ecycle_provider_apply")
@Data
public class ProviderApply implements Serializable {
    /**
     * id
     */
    @TableId
    private UUID id;

    /**
     * 附件
     */
    private UUID belongId;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 申请单状态
     */
    private ProviderApplyStatus status;

    /**
     * 审批意见
     */
    private String approvalMessage;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date auditTime;

    /**
     * 小程序用户
     */
    private UUID userId;
}