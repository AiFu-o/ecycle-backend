package com.ecycle.storage.exception;

/**
 * @author wangweichen
 * @Date 2024/4/12
 * @Description 文件上传异常
 */
public class AttachmentException extends RuntimeException{
    public AttachmentException() {
        super();
    }

    public AttachmentException(String message) {
        super(message);
    }

    public AttachmentException(String message, Throwable cause) {
        super(message, cause);
    }

}
