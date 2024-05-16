package com.ecycle.commodity.service.impl;

import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.service.CommodityService;
import com.ecycle.commodity.service.NotificationService;
import com.ecycle.commodity.service.feign.NotificationFeignService;
import com.ecycle.commodity.web.info.CreateNotificationRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/5/16
 * @Description TODO
 */
@Service
@Log4j2
public class NotificationServiceImpl implements NotificationService {

    @Resource
    private NotificationFeignService notificationFeignService;

    @Resource
    private CommodityService commodityService;

    @Override
    public void quoteMessage(UUID commodityId, UUID receiverId) {
        try {
            Commodity commodity = commodityService.getById(commodityId);

            CreateNotificationRequest request = new CreateNotificationRequest();
            request.setType("QUOTE");
            request.setContent("您收到一条新的交易报价，请尽快选择合适的价格进行交易。");
            request.setCoverId(commodity.getCoverFileId());
            request.setLinkId(commodity.getId());
            request.setReceiverId(receiverId);
            notificationFeignService.send(request);
        } catch (Exception e) {
            log.error("发送消息失败", e);
        }
    }

}
