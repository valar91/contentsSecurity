package com.ziroom.eunomia.common.exception;

public class SensitiveException extends EunomiaException {

    private static final long serialVersionUID = 6298408101423410205L;

    public SensitiveException(String message) {
        super(message);
    }

    public SensitiveException(String message, Throwable cause) {
        super(message, cause);
    }
}
