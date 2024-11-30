package com.laundrygo.shorturl.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ShortUrlGenerateRequest {
    @NotBlank
    private String originalUrl;
}
