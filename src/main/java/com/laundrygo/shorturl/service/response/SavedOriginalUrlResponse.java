package com.laundrygo.shorturl.service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedOriginalUrlResponse {

    private String originalUrl;

    public static SavedOriginalUrlResponse of(String originalUrl) {
        return SavedOriginalUrlResponse.builder()
                .originalUrl(originalUrl)
                .build();
    }

}
