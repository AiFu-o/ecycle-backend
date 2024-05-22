package com.ecycle.auth.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.auth.constant.ProviderApplyStatus;
import com.ecycle.auth.exception.ProviderApplyException;
import com.ecycle.auth.mapper.ProviderApplyMapper;
import com.ecycle.auth.model.ProviderApply;
import com.ecycle.auth.model.User;
import com.ecycle.auth.service.ProviderApplyService;
import com.ecycle.auth.service.UserService;
import com.ecycle.auth.web.info.ProviderApplyQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.utils.CurrentUserInfoUtils;
import com.ecycle.common.utils.MybatisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author wangweichen
 * @description 针对表【ecycle_provider_apply(回收商申请单)】的数据库操作Service实现
 * @createDate 2024-04-15 14:57:15
 */
@Service
public class ProviderApplyServiceImpl extends ServiceImpl<ProviderApplyMapper, ProviderApply>
        implements ProviderApplyService {

    @Resource
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(UUID billId, String approvalMessage) {
        ProviderApply providerApply = super.getById(billId.toString());
        if (null == providerApply) {
            throw new ProviderApplyException("找不到回收商申请单");
        }
        if (ProviderApplyStatus.PENDING != providerApply.getStatus()) {
            throw new ProviderApplyException("申请单状态异常");
        }
        if(StringUtils.isEmpty(approvalMessage)){
            throw new ProviderApplyException("审批意见不能为空");
        }
        User user = userService.getById(providerApply.getUserId());
        userService.addRoleByCode("provider", user);
        providerApply.setStatus(ProviderApplyStatus.APPROVE);
        providerApply.setAuditTime(new Date());
        providerApply.setApprovalMessage(approvalMessage);
        updateById(providerApply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reject(UUID billId, String approvalMessage) {
        ProviderApply providerApply = super.getById(billId.toString());
        if (null == providerApply) {
            throw new ProviderApplyException("找不到回收商申请单");
        }
        if (ProviderApplyStatus.PENDING != providerApply.getStatus()) {
            throw new ProviderApplyException("申请单状态异常");
        }
        if(StringUtils.isEmpty(approvalMessage)){
            throw new ProviderApplyException("审批意见不能为空");
        }
        providerApply.setStatus(ProviderApplyStatus.REJECT);
        providerApply.setAuditTime(new Date());
        providerApply.setApprovalMessage(approvalMessage);
        updateById(providerApply);
    }

    private void validateSaveData(ProviderApply apply) {
        UUID userId;
        try {
            userId = CurrentUserInfoUtils.getCurrentUserId();
        } catch (Exception e) {
            throw new ProviderApplyException("找不到小程序用户", e);
        }

        if (null != apply.getUserId() && !apply.getUserId().equals(userId)) {
            throw new ProviderApplyException("用户异常");
        }

        if (StringUtils.isEmpty(apply.getIdCard())) {
            throw new ProviderApplyException("身份证不能为空");
        } else {
            String regex = "^[1-9]\\d{5}(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dX]$";
            if (!Pattern.matches(regex, apply.getIdCard())) {
                throw new ProviderApplyException("身份证格式有误");
            }
        }

        if (StringUtils.isEmpty(apply.getTelephone())) {
            throw new ProviderApplyException("手机号不能为空");
        } else {
            String regex = "^1[3456789]\\d{9}$";
            if (!Pattern.matches(regex, apply.getTelephone())) {
                throw new ProviderApplyException("手机号格式有误");
            }
        }

        if (StringUtils.isEmpty(apply.getName())) {
            throw new ProviderApplyException("姓名不能为空");
        }
        apply.setUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(ProviderApply apply) {
        validateSaveData(apply);
        apply.setId(UUID.randomUUID());
        apply.setStatus(ProviderApplyStatus.PENDING);
        return super.save(apply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(ProviderApply apply) {
        validateSaveData(apply);
        if (null == apply.getId()) {
            throw new ProviderApplyException("id不能为空");
        }
        ProviderApply historyApply = super.getById(apply.getId());
        if (null == historyApply) {
            throw new ProviderApplyException("找不到申请单");
        }
        if (null == historyApply.getStatus() || historyApply.getStatus() != ProviderApplyStatus.PENDING) {
            throw new ProviderApplyException("申请单状态异常");
        }
        historyApply.setName(apply.getName());
        historyApply.setIdCard(apply.getIdCard());
        historyApply.setTelephone(apply.getTelephone());
        return updateById(historyApply);
    }

    @Override
    public PageQueryResponse pageQueryAll(ProviderApplyQueryRequest body) {
        QueryChainWrapper<ProviderApply> queryChainWrapper = super.query();
        if(StringUtils.isNotEmpty(body.getName())){
            queryChainWrapper.like("name", "%" + body.getName() + "%");
        }
        queryChainWrapper.orderByAsc("create_time");

        MybatisUtils<ProviderApply> mybatisUtils = new MybatisUtils<>();
        return mybatisUtils.pageQuery(queryChainWrapper, body);
    }

    @Override
    public ProviderApply loadMine() {
        UUID userId;
        try {
            userId = CurrentUserInfoUtils.getCurrentUserId();
        } catch (Exception e) {
            throw new ProviderApplyException("找不到小程序用户", e);
        }

        return baseMapper.loadMine(userId);
    }

}




