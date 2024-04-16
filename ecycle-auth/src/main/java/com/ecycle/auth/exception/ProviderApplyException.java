package com.ecycle.auth.exception;

/**
 * @author wangweichen
 * @Date 2024/4/16
 * @Description 回收商申请单异常处理
 */
public class ProviderApplyException extends RuntimeException {
    public ProviderApplyException(String message){
        super(message);
    }

    public ProviderApplyException(String message, Exception e){
        super(message, e);
    }
}
