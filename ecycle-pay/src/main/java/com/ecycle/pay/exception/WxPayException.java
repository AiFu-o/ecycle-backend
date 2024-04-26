package com.ecycle.pay.exception;

/**
 * @author wangweichen
 * @Date 2024/4/26
 * @Description TODO
 */
public class WxPayException extends RuntimeException{
    public WxPayException(String message){
        super(message);
    }
}
