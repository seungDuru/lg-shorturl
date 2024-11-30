package com.laundrygo.shorturl.service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedShortUrlResponse {

    private String shortUrl;

    public static SavedShortUrlResponse of(String shortUrl) {
        return SavedShortUrlResponse.builder()
                .shortUrl(shortUrl)
                .build();
    }

}
