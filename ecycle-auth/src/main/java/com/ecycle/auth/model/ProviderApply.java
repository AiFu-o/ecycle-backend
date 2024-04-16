package com.ecycle.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.ecycle.auth.constant.ProviderApplyStatus;
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
    private Date createTime;

    /**
     * 审批时间
     */
    private Date auditTime;

    /**
     * 小程序用户
     */
    private UUID userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ProviderApply other = (ProviderApply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBelongId() == null ? other.getBelongId() == null : this.getBelongId().equals(other.getBelongId()))
            && (this.getIdCard() == null ? other.getIdCard() == null : this.getIdCard().equals(other.getIdCard()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getTelephone() == null ? other.getTelephone() == null : this.getTelephone().equals(other.getTelephone()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getApprovalMessage() == null ? other.getApprovalMessage() == null : this.getApprovalMessage().equals(other.getApprovalMessage()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getAuditTime() == null ? other.getAuditTime() == null : this.getAuditTime().equals(other.getAuditTime()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBelongId() == null) ? 0 : getBelongId().hashCode());
        result = prime * result + ((getIdCard() == null) ? 0 : getIdCard().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getTelephone() == null) ? 0 : getTelephone().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getApprovalMessage() == null) ? 0 : getApprovalMessage().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getAuditTime() == null) ? 0 : getAuditTime().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", belongId=").append(belongId);
        sb.append(", idCard=").append(idCard);
        sb.append(", name=").append(name);
        sb.append(", telephone=").append(telephone);
        sb.append(", status=").append(status);
        sb.append(", approvalMessage=").append(approvalMessage);
        sb.append(", createTime=").append(createTime);
        sb.append(", auditTime=").append(auditTime);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}