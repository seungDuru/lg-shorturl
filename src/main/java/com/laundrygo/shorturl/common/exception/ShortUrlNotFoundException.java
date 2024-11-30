package com.laundrygo.shorturl.common.exception;

public class ShortUrlNotFoundException extends LgShortUrlException {
    public ShortUrlNotFoundException() {
        super(ErrorCode.SHORT_URL_NOT_FOUND);
    }
}
