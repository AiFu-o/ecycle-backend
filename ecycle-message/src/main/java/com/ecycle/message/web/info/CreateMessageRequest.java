package com.ecycle.message.web.info;

import com.ecycle.message.constant.NotificationType;
import lombok.Data;

import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/5/16
 * @Description TODO
 */
@Data
public class CreateMessageRequest {
    /**
     * 消息类型
     */
    private NotificationType type;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 接收人
     */
    private UUID receiverId;

    /**
     * 关联模型 id
     */
    private UUID linkId;

    /**
     * 封面 id
     */
    private UUID coverId;
}
