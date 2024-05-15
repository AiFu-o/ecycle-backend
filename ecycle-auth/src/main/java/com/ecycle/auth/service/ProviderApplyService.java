package com.ecycle.auth.service;

import com.ecycle.auth.model.ProviderApply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecycle.auth.web.info.ProviderApplyQueryRequest;
import com.ecycle.common.context.PageQueryResponse;

import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_provider_apply(回收商申请单)】的数据库操作Service
 * @createDate 2024-04-15 14:57:15
 */
public interface ProviderApplyService extends IService<ProviderApply> {

    /**
     * 通过回收商申请
     *
     * @param billId          申请单 id
     * @param approvalMessage 审批意见
     */
    void approve(UUID billId, String approvalMessage);

    /**
     * 驳回回收商申请
     *
     * @param billId          申请单 id
     * @param approvalMessage 审批意见
     */
    void reject(UUID billId, String approvalMessage);

    /**
     * 回收商申请单创建
     *
     * @param apply 申请单
     * @return 是否创建成功
     */
    boolean save(ProviderApply apply);

    /**
     * 回收商申请单编辑
     *
     * @param apply 申请单
     * @return 是否保存成功
     */
    boolean edit(ProviderApply apply);

    /**
     * 回收商申请单分页查询
     *
     * @param body 查询参数
     * @return 查询结果
     */
    PageQueryResponse pageQueryAll(ProviderApplyQueryRequest body);

    /**
     * 自己申请的回收商申请单分页查询
     *
     * @param body 查询参数
     * @return 查询结果
     */
    PageQueryResponse pageQueryMineAll(ProviderApplyQueryRequest body);

}
