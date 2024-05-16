package com.ecycle.commodity.web;

import com.ecycle.commodity.exception.AddressException;
import com.ecycle.commodity.exception.BiddingOrderException;
import com.ecycle.commodity.exception.OrderException;
import com.ecycle.commodity.model.UserAddress;
import com.ecycle.commodity.service.OrderService;
import com.ecycle.commodity.service.UserAddressService;
import com.ecycle.commodity.web.info.OrderQueryRequest;
import com.ecycle.common.context.PageQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.context.RestResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/3/14
 * @Description 订单接口
 */
@RestController
@Log4j2
@RequestMapping("/address")
public class AddressController {

    @Resource
    private UserAddressService userAddressService;

    @GetMapping("/queryMineAll")
    public RestResponse<List<UserAddress>> queryMineAll() {
        try {
            return RestResponse.success(userAddressService.queryMineAll());
        } catch (Exception e) {
            log.error("未知异常", e);
            return RestResponse.validfail("未知异常");
        }
    }

    @PostMapping("/save")
    public RestResponse<UUID> save(@RequestBody UserAddress request) {
        try {
            return RestResponse.success(userAddressService.createAddress(request));
        } catch (AddressException e) {
            log.error("保存地址失败", e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("保存地址失败", e);
            return RestResponse.validfail("未知异常");
        }
    }

    @PutMapping("/save")
    public RestResponse<UUID> update(@RequestBody UserAddress request) {
        try {
            return RestResponse.success(userAddressService.updateAddress(request));
        } catch (AddressException e) {
            log.error("保存地址失败", e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("保存地址失败", e);
            return RestResponse.validfail("未知异常");
        }
    }

    @PutMapping("/default/{addressId}")
    public RestResponse<Boolean> defaultAddress(@PathVariable(name = "addressId") UUID addressId) {
        try {
            userAddressService.defaultAddress(addressId);
            return RestResponse.success(true);
        } catch (AddressException e) {
            log.error("修改默认地址失败", e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("修改默认地址失败", e);
            return RestResponse.validfail("未知异常");
        }
    }

    @DeleteMapping("/del/{addressId}")
    public RestResponse<Boolean> del(@PathVariable(name = "addressId") UUID addressId) {
        try {
            userAddressService.removeById(addressId);
            return RestResponse.success(true);
        } catch (AddressException e) {
            log.error("删除地址失败", e);
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            log.error("删除地址失败", e);
            return RestResponse.validfail("未知异常");
        }
    }

}
