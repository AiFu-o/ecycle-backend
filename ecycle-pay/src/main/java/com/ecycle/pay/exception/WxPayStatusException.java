package com.ecycle.pay.exception;

/**
 * @author wangweichen
 * @Date 2024/4/26
 * @Description TODO
 */
public class WxPayStatusException extends RuntimeException {
    public WxPayStatusException(String message){
        super(message);
    }
}
