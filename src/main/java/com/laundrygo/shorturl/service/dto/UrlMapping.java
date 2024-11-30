package com.laundrygo.shorturl.service.dto;

import com.laundrygo.shorturl.domain.UrlMappingEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UrlMapping {

    private String originalUrl;
    private String shortUrl;
    private Long requestCount;

    public static UrlMapping toDto(UrlMappingEntity entity) {
        return UrlMapping.builder()
                .originalUrl(entity.getOriginalUrl())
                .shortUrl(entity.getShortUrl())
                .requestCount(entity.getRequestCount())
                .build();
    }

}
