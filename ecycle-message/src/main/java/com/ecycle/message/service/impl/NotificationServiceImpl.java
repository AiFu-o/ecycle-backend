package com.ecycle.message.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.utils.CurrentUserInfoUtils;
import com.ecycle.common.utils.MybatisUtils;
import com.ecycle.message.exception.NotificationException;
import com.ecycle.message.model.Notification;
import com.ecycle.message.service.NotificationService;
import com.ecycle.message.mapper.NotificationMapper;
import com.ecycle.message.web.info.CreateMessageRequest;
import com.ecycle.message.web.info.MessageQueryRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_notification(通知)】的数据库操作Service实现
* @createDate 2024-05-16 09:39:15
*/
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification>
    implements NotificationService{

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(CreateMessageRequest request) {
        if(null == request.getReceiverId()){
            throw new NotificationException("接收人不能为空");
        }
        if(null == request.getType()){
            throw new NotificationException("消息类型不能为空");
        }
        if(StringUtils.isEmpty(request.getContent())){
            throw new NotificationException("消息内容不能为空");
        }

        Notification notification = new Notification();
        notification.setId(UUID.randomUUID());
        notification.setReceiverId(request.getReceiverId());
        notification.setContent(request.getContent());
        notification.setType(request.getType());

        if(null != request.getCoverId()){
            notification.setCoverId(request.getCoverId());
        }
        if(null != request.getLinkId()){
            notification.setLinkId(request.getLinkId());
        }
        save(notification);
    }

    @Override
    public PageQueryResponse queryMineAll(MessageQueryRequest request) {
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        Assert.notNull(userId, "找不到用户");

        QueryChainWrapper<Notification> queryChainWrapper = super.query();
        queryChainWrapper.eq("receiver_id", userId);
        queryChainWrapper.eq("is_del", false);
        queryChainWrapper.orderByDesc("create_time");

        MybatisUtils<Notification> mybatisUtils = new MybatisUtils<>();

        return mybatisUtils.pageQuery(queryChainWrapper, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void read(UUID messageId) {
        Notification notification = getById(messageId);
        if(null == notification){
            throw new NotificationException("找不到通知");
        }
        notification.setIsRead(true);
        updateById(notification);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(UUID messageId) {
        Notification notification = getById(messageId);
        if(null == notification){
            throw new NotificationException("找不到通知");
        }
        notification.setIsDel(true);
        updateById(notification);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delReadMessage() {
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        Assert.notNull(userId, "找不到用户");
        baseMapper.delReadMessage(userId);
    }
}




