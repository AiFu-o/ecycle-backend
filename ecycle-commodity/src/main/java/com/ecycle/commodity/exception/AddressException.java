package com.ecycle.commodity.exception;

/**
 * @author wangweichen
 * @Date 2024/4/19
 * @Description 订单异常
 */
public class AddressException extends RuntimeException{

    public AddressException(String message){
        super(message);
    }
}
