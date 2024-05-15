package com.ecycle.storage.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 附件
 * @author wangweichen
 * @TableName ecycle_attachment_info
 */
@TableName(value ="ecycle_attachment_info")
@Data
public class AttachmentInfo implements Serializable {
    /**
     * id
     */
    @TableId
    private UUID id;

    /**
     * 文件分类
     */
    private String category;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * varchar
     */
    private String fileType;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件描述
     */
    private String info;

    /**
     * belongId
     */
    private UUID belongId;

    /**
     * 上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人 id
     */
    private UUID creatorId;

    /**
     * 存储地址
     */
    private String address;

    /**
     * 存储协议
     */
    private String protocol;

}