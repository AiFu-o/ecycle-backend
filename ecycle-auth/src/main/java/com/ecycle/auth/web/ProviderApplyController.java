package com.ecycle.auth.web;

import com.ecycle.auth.constant.ProviderApplyStatus;
import com.ecycle.auth.exception.ProviderApplyException;
import com.ecycle.auth.exception.UserException;
import com.ecycle.auth.model.ProviderApply;
import com.ecycle.auth.service.ProviderApplyService;
import com.ecycle.auth.web.info.ProviderApplyApprovalRequest;
import com.ecycle.auth.web.info.ProviderApplyQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.context.RestResponse;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/4/15
 * @Description 回收商申请单接口
 */
@RestController
@RequestMapping("/provider-apply")
public class ProviderApplyController {

    @Resource
    private ProviderApplyService providerApplyService;

    @PutMapping("/completed/{billId}")
    public RestResponse<String> completed(@PathVariable(name = "billId") UUID billId,
                                          @RequestBody ProviderApplyApprovalRequest providerApplyApprovalRequest) {
        try {
            if (providerApplyApprovalRequest.getApprove()) {
                providerApplyService.approve(billId, providerApplyApprovalRequest.getApprovalMessage());
            } else {
                providerApplyService.reject(billId, providerApplyApprovalRequest.getApprovalMessage());
            }
        } catch (UserException | ProviderApplyException e) {
            e.printStackTrace();
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.validfail("未知异常");
        }
        return RestResponse.success();
    }

    @GetMapping("/load/{id}")
    public RestResponse<ProviderApply> load(@PathVariable(name = "id") UUID id) {
        try {
            return RestResponse.success(providerApplyService.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.validfail("未知异常");
        }
    }

    @PutMapping("/save")
    public RestResponse<UUID> update(@RequestBody ProviderApply providerApply) {
        Assert.notNull(providerApply, "申请单不能为空");
        try {
            boolean isSave = providerApplyService.save(providerApply);
            if (!isSave) {
                return RestResponse.validfail("保存失败");
            }
        } catch (UserException | ProviderApplyException e) {
            e.printStackTrace();
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.validfail("未知异常");
        }
        return RestResponse.success(providerApply.getId());
    }

    @PostMapping("/save")
    public RestResponse<UUID> save(@RequestBody ProviderApply providerApply) {
        Assert.notNull(providerApply, "申请单不能为空");
        try {
            boolean isSave = providerApplyService.save(providerApply);
            if (!isSave) {
                return RestResponse.validfail("保存失败");
            }
        } catch (ProviderApplyException e) {
            e.printStackTrace();
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.validfail("未知异常");
        }
        return RestResponse.success(providerApply.getId());
    }

    @PostMapping("/pageQueryAll")
    public RestResponse<PageQueryResponse> pageQueryAll(@RequestBody ProviderApplyQueryRequest body) {
        try {
            PageQueryResponse result = providerApplyService.pageQueryAll(body);
            return RestResponse.success(result);
        } catch (ProviderApplyException e) {
            e.printStackTrace();
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.validfail("未知异常");
        }
    }

    @PostMapping("/pageQueryMineAll")
    public RestResponse<PageQueryResponse> pageQueryMineAll(@RequestBody ProviderApplyQueryRequest body) {
        try {
            PageQueryResponse result = providerApplyService.pageQueryMineAll(body);
            return RestResponse.success(result);
        } catch (ProviderApplyException e) {
            e.printStackTrace();
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.validfail("未知异常");
        }
    }

}
