package com.docsys.manager.exception;

/**
 * 自定义异常(CustomException)
 * @author dolyw.com
 * @date 2018/8/30 13:59
 */
public class CustomTokenExpireException extends RuntimeException {

    public CustomTokenExpireException(String msg){
        super(msg);
    }

    public CustomTokenExpireException() {
        super();
    }
}
