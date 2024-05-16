package com.ecycle.message.service;

import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.message.model.Notification;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecycle.message.web.info.CreateMessageRequest;
import com.ecycle.message.web.info.MessageQueryRequest;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_notification(通知)】的数据库操作Service
* @createDate 2024-05-16 09:39:15
*/
public interface NotificationService extends IService<Notification> {

    /**
     * 发送消息
     * @param request 消息参数
     */
    void send(CreateMessageRequest request);

    /**
     * 分页查询我的消息列表
     * @param request 查询参数
     * @return 消息列表
     */
    PageQueryResponse queryMineAll(MessageQueryRequest request);

    /**
     * 消息已读
     * @param messageId 消息 id
     */
    void read(UUID messageId);

    /**
     * 消息删除
     * @param messageId 消息 id
     */
    void del(UUID messageId);

    /**
     * 删除我的已读消息
     */
    void delReadMessage();

}
