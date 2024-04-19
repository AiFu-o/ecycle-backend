package com.ecycle.commodity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.ecycle.commodity.constant.CommodityStatus;
import lombok.Data;

/**
 * 商品
 * @author wangweichen
 * @TableName ecycle_commodity
 */
@TableName(value ="ecycle_commodity")
@Data
public class Commodity implements Serializable {
    /**
     * id
     */
    @TableId
    private UUID id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品描述
     */
    private String desc;

    /**
     * 商品种类
     */
    private UUID categoryId;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date modifyTime;

    /**
     * 创建人id
     */
    private UUID creatorId;

    /**
     * 附件 id
     */
    private UUID belongId;

    /**
     * 浏览量
     */
    private Integer pageViews;

    /**
     * 商品状态
     */
    private CommodityStatus status;

    /**
     * 成交价
     */
    private BigDecimal dealPrice;

    /**
     * 服务费
     */
    private BigDecimal serviceCharge;

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
        Commodity other = (Commodity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getCreatorId() == null ? other.getCreatorId() == null : this.getCreatorId().equals(other.getCreatorId()))
            && (this.getBelongId() == null ? other.getBelongId() == null : this.getBelongId().equals(other.getBelongId()))
            && (this.getPageViews() == null ? other.getPageViews() == null : this.getPageViews().equals(other.getPageViews()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDealPrice() == null ? other.getDealPrice() == null : this.getDealPrice().equals(other.getDealPrice()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getCreatorId() == null) ? 0 : getCreatorId().hashCode());
        result = prime * result + ((getBelongId() == null) ? 0 : getBelongId().hashCode());
        result = prime * result + ((getPageViews() == null) ? 0 : getPageViews().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDealPrice() == null) ? 0 : getDealPrice().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", desc=").append(desc);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", belongId=").append(belongId);
        sb.append(", pageViews=").append(pageViews);
        sb.append(", status=").append(status);
        sb.append(", dealPrice=").append(dealPrice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}