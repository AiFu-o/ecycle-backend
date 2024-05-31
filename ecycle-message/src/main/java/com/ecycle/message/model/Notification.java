package com.ecycle.message.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.ecycle.message.constant.NotificationType;
import lombok.Data;

/**
 * 通知
 * @TableName ecycle_notification
 */
@TableName(value ="ecycle_notification")
@Data
public class Notification implements Serializable {
    /**
     * id
     */
    @TableId
    private UUID id;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 接收人
     */
    private UUID receiverId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    private Boolean isDel;

    /**
     * 是否已读
     */
    private Boolean isRead;

    /**
     * 消息类型
     */
    private NotificationType type;

    /**
     * 关联模型 id
     */
    private UUID linkId;

    /**
     * 封面 id
     */
    private UUID coverId;

}