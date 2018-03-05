package com.ziroom.eunomia.common.exception;

public class EunomiaException extends RuntimeException {

    public EunomiaException() {
    }

    public EunomiaException(String message) {
        super(message);
    }

    public EunomiaException(String message, Throwable cause) {
        super(message, cause);
    }

    public EunomiaException(Throwable cause) {
        super(cause);
    }

    public EunomiaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
