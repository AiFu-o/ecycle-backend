package com.ecycle.message.web;

import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.context.RestResponse;
import com.ecycle.message.exception.NotificationException;
import com.ecycle.message.model.Notification;
import com.ecycle.message.service.NotificationService;
import com.ecycle.message.web.info.CreateMessageRequest;
import com.ecycle.message.web.info.MessageQueryRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/5/16
 * @Description TODO
 */
@RestController
@Log4j2
@RequestMapping("/notification")
public class MessageController {

    @Resource
    private NotificationService notificationService;

    @PostMapping("/send")
    public RestResponse<Boolean> send(@RequestBody CreateMessageRequest request) {
        try {
            notificationService.send(request);
        } catch (NotificationException e) {
            log.error("创建通知异常", e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("创建通知异常", e);
            return RestResponse.validfail("未知异常");
        }
        return RestResponse.success(true);
    }

    @PutMapping("/del/{messageId}")
    public RestResponse<Boolean> del(@PathVariable(name = "messageId") UUID messageId) {
        try {
            notificationService.del(messageId);
        } catch (NotificationException e) {
            log.error("创建通知异常", e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("创建通知异常", e);
            return RestResponse.validfail("未知异常");
        }
        return RestResponse.success(true);
    }

    @PutMapping("/read/{messageId}")
    public RestResponse<Boolean> read(@PathVariable(name = "messageId") UUID messageId) {
        try {
            notificationService.read(messageId);
        } catch (NotificationException e) {
            log.error("创建通知异常", e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("创建通知异常", e);
            return RestResponse.validfail("未知异常");
        }
        return RestResponse.success(true);
    }

    @PutMapping("/delReadMessage")
    public RestResponse<Boolean> delReadMessage() {
        try {
            notificationService.delReadMessage();
        } catch (NotificationException e) {
            log.error("创建通知异常", e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("创建通知异常", e);
            return RestResponse.validfail("未知异常");
        }
        return RestResponse.success(true);
    }

    @PostMapping("/queryMineAll")
    public RestResponse<PageQueryResponse> send(@RequestBody MessageQueryRequest request) {
        try {
            return RestResponse.success(notificationService.queryMineAll(request));
        } catch (NotificationException e) {
            log.error("创建通知异常", e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("创建通知异常", e);
            return RestResponse.validfail("未知异常");
        }
    }

}
