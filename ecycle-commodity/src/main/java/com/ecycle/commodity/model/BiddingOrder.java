package com.ecycle.commodity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ecycle.commodity.constant.BiddingOrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * 订单
 * @TableName ecycle_order
 */
@TableName(value ="ecycle_bidding_order")
@Data
public class BiddingOrder implements Serializable {
    /**
     * id
     */
    @TableId
    private UUID id;

    /**
     * 订单编号
     */
    private String billCode;

    /**
     * 商品 id
     */
    private UUID commodityId;

    /**
     * 创建人 id
     */
    private UUID creatorId;

    /**
     * 状态
     */
    private BiddingOrderStatus status;

    /**
     * 商品价格
     */
    private BigDecimal commodityAmount;

    /**
     * 服务费应收
     */
    private BigDecimal serviceChargeReceivable;

    /**
     * 服务费实收
     */
    private BigDecimal serviceChargeReceived;

    /**
     * 出价时间
     */
    private Date createTime;

    /**
     * 确定出价时间
     */
    private Date confirmTime;

    /**
     * 服务费支付时间
     */
    private Date serviceChargePayTime;

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
        BiddingOrder other = (BiddingOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBillCode() == null ? other.getBillCode() == null : this.getBillCode().equals(other.getBillCode()))
            && (this.getCommodityId() == null ? other.getCommodityId() == null : this.getCommodityId().equals(other.getCommodityId()))
            && (this.getCreatorId() == null ? other.getCreatorId() == null : this.getCreatorId().equals(other.getCreatorId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCommodityAmount() == null ? other.getCommodityAmount() == null : this.getCommodityAmount().equals(other.getCommodityAmount()))
            && (this.getServiceChargeReceivable() == null ? other.getServiceChargeReceivable() == null : this.getServiceChargeReceivable().equals(other.getServiceChargeReceivable()))
            && (this.getServiceChargeReceived() == null ? other.getServiceChargeReceived() == null : this.getServiceChargeReceived().equals(other.getServiceChargeReceived()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getConfirmTime() == null ? other.getConfirmTime() == null : this.getConfirmTime().equals(other.getConfirmTime()))
            && (this.getServiceChargePayTime() == null ? other.getServiceChargePayTime() == null : this.getServiceChargePayTime().equals(other.getServiceChargePayTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBillCode() == null) ? 0 : getBillCode().hashCode());
        result = prime * result + ((getCommodityId() == null) ? 0 : getCommodityId().hashCode());
        result = prime * result + ((getCreatorId() == null) ? 0 : getCreatorId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCommodityAmount() == null) ? 0 : getCommodityAmount().hashCode());
        result = prime * result + ((getServiceChargeReceivable() == null) ? 0 : getServiceChargeReceivable().hashCode());
        result = prime * result + ((getServiceChargeReceived() == null) ? 0 : getServiceChargeReceived().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getConfirmTime() == null) ? 0 : getConfirmTime().hashCode());
        result = prime * result + ((getServiceChargePayTime() == null) ? 0 : getServiceChargePayTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", billCode=").append(billCode);
        sb.append(", commodityId=").append(commodityId);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", status=").append(status);
        sb.append(", commodityAmount=").append(commodityAmount);
        sb.append(", serviceChargeReceivable=").append(serviceChargeReceivable);
        sb.append(", serviceChargeReceived=").append(serviceChargeReceived);
        sb.append(", createTime=").append(createTime);
        sb.append(", confirmTime=").append(confirmTime);
        sb.append(", serviceChargePayTime=").append(serviceChargePayTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}