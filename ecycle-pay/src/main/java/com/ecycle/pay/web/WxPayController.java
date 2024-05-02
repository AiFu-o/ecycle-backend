package com.ecycle.pay.web;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.alibaba.nacos.shaded.com.google.gson.GsonBuilder;
import com.ecycle.common.context.RestResponse;
import com.ecycle.pay.constant.OrderTypeEnum;
import com.ecycle.pay.exception.WxPayException;
import com.ecycle.pay.exception.WxPayStatusException;
import com.ecycle.pay.service.WxPayService;
import com.ecycle.pay.web.info.WxCallBackResponse;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/4/26
 * @Description TODO
 */
@RestController
@Log4j2
@RequestMapping("/wx-pay")
public class WxPayController {

    @Resource
    private WxPayService wxPayService;

    @PostMapping("/pay-notify")
    public WxCallBackResponse payNotifyCallBack(@RequestHeader Map<String, String> headers,
                                                @RequestBody String body) {
        return wxPayService.payNotifyCallBack(body, headers);
    }

    @PostMapping("/service-charge/prepay")
    public RestResponse<String> serviceChargePrePay(@RequestParam("orderId") UUID orderId,
                                                  @RequestParam("amount") Integer amount) {
        try {
            PrepayWithRequestPaymentResponse response = wxPayService.prepay(OrderTypeEnum.SERVICE_CHARGE, orderId, amount);
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            return RestResponse.success(gson.toJson(response));
        } catch (WxPayException | WxPayStatusException e) {
            log.error(e.getMessage(), e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RestResponse.validfail("未知异常");
        }
    }

}
