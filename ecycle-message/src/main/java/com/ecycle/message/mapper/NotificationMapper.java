package com.ecycle.message.mapper;

import com.ecycle.message.model.Notification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_notification(通知)】的数据库操作Mapper
* @createDate 2024-05-16 09:39:15
* @Entity com.ecycle.commodity.model.Notification
*/
public interface NotificationMapper extends BaseMapper<Notification> {

    /**
     * 清除已读消息
     * @param userId 当前用户 id
     */
    void delReadMessage(@Param("userId") UUID userId);
}




