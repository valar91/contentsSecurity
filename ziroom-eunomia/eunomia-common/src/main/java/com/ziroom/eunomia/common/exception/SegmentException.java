package com.ziroom.eunomia.common.exception;

public class SegmentException extends RuntimeException {

    private static final long serialVersionUID = 5317268492037442732L;

    public SegmentException() {
        super();
    }

    public SegmentException(String message) {
        super(message);
    }

    public SegmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public SegmentException(Throwable cause) {
        super(cause);
    }

    protected SegmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
