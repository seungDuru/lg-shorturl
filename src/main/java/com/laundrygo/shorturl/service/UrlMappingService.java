package com.laundrygo.shorturl.service;

import com.laundrygo.shorturl.common.exception.ShortUrlNotFoundException;
import com.laundrygo.shorturl.service.component.UrlMappingProvider;
import com.laundrygo.shorturl.service.dto.UrlMapping;
import com.laundrygo.shorturl.service.response.SavedOriginalUrlResponse;
import com.laundrygo.shorturl.service.response.SavedShortUrlResponse;
import com.laundrygo.shorturl.service.response.UrlMappingsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.laundrygo.shorturl.common.constants.CommonConstants.EIGHT;
import static com.laundrygo.shorturl.common.utils.RandomStringGeneratorUtils.generateRandomString;

@RequiredArgsConstructor
@Service
public class UrlMappingService {

    private final UrlMappingProvider urlMappingProvider;

    @Transactional
    public SavedShortUrlResponse generateShortUrl(String originalUrl) {
        return urlMappingProvider.findUrlMappingByOriginalUrl(originalUrl)
                .map(urlMapping -> {
                    urlMappingProvider.increaseRequestCount(urlMapping.getShortUrl());
                    return SavedShortUrlResponse.of(urlMapping.getShortUrl());
                })
                .orElseGet(() -> {
                    String shortUrl = generateUniqueShortUrl();
                    urlMappingProvider.saveUrlMapping(originalUrl, shortUrl);
                    return SavedShortUrlResponse.of(shortUrl);
                });
    }

    @Transactional(readOnly = true)
    public SavedOriginalUrlResponse getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingProvider.findUrlMappingByShortUrl(shortUrl)
                .orElseThrow(ShortUrlNotFoundException::new);
        return SavedOriginalUrlResponse.of(urlMapping.getOriginalUrl());
    }

    @Transactional(readOnly = true)
    public UrlMappingsResponse getAllUrlMappings() {
        return UrlMappingsResponse.of(urlMappingProvider.getAllUrlMappings());
    }

    private String generateUniqueShortUrl() {
        String shortUrl;
        do {
            shortUrl = generateRandomString(EIGHT);
        } while (urlMappingProvider.findUrlMappingByShortUrl(shortUrl).isPresent());
        return shortUrl;
    }

}
