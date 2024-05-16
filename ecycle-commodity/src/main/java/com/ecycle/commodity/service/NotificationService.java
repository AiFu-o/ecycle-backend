package com.ecycle.commodity.service;

import java.util.UUID;

/**
 * @author wangweichen
 */
public interface NotificationService {

    /**
     * 发送收到报价消息
     * @param commodityId 商品 id
     * @param receiverId 接收人 id
     */
    void quoteMessage(UUID commodityId, UUID receiverId);
}
