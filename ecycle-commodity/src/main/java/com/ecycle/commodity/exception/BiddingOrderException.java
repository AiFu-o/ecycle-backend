package com.ecycle.commodity.exception;

/**
 * @author wangweichen
 * @Date 2024/4/19
 * @Description 订单异常
 */
public class BiddingOrderException extends RuntimeException{

    public BiddingOrderException(String message){
        super(message);
    }
}
