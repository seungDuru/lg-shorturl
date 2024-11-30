package com.laundrygo.shorturl.common.exception;

public class LgShortUrlException extends RuntimeException {
    private final ErrorCode errorCode;

    public LgShortUrlException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public LgShortUrlException(ErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
