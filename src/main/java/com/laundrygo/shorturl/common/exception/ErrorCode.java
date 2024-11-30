package com.laundrygo.shorturl.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SHORT_URL_NOT_FOUND(HttpStatus.NOT_FOUND, "Short URL 을 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 회원이 없습니다."),
    ;
    private final HttpStatus status;
    private final String message;
}
