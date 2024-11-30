package com.laundrygo.shorturl.common.exception;

import static com.laundrygo.shorturl.common.exception.ErrorCode.MEMBER_NOT_FOUND;

public class MemberNotFoundException extends LgShortUrlException {
    public MemberNotFoundException() {
        super(MEMBER_NOT_FOUND);
    }
}
