package com.laundrygo.shorturl.service.response;

import com.laundrygo.shorturl.service.dto.UrlMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlMappingsResponse {

    List<UrlMapping> urlMappings;

    public static UrlMappingsResponse of(List<UrlMapping> urlMappings) {
        return UrlMappingsResponse.builder()
                .urlMappings(urlMappings)
                .build();
    }
}