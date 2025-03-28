package com.ecycle.commodity.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import lombok.Data;

/**
 * 用户地址
 * @author wangweichen
 * @TableName ecycle_user_address
 */
@TableName(value ="ecycle_user_address")
@Data
public class UserAddress implements Serializable {
    /**
     * id
     */
    @TableId
    private UUID id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 省
     */
    private String province;

    /**
     * 省份编号
     */
    private String provinceCode;

    /**
     * 区
     */
    private String district;

    /**
     * 区编号
     */
    private String districtCode;

    /**
     * 市
     */
    private String city;

    /**
     * 市编号
     */
    private String cityCode;

    /**
     * 街道
     */
    private String stress;

    /**
     * 街道编号
     */
    private String stressCode;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 用户
     */
    private UUID creatorId;

    /**
     * 是否默认地址
     */
    private Boolean defaultAddress;

}