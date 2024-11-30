package com.laundrygo.shorturl.service.component;

import com.laundrygo.shorturl.common.exception.ShortUrlNotFoundException;
import com.laundrygo.shorturl.domain.UrlMappingEntity;
import com.laundrygo.shorturl.repository.UrlMappingRepository;
import com.laundrygo.shorturl.service.dto.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UrlMappingProvider {

    private final UrlMappingRepository urlMappingRepository;

    public Optional<UrlMapping> findUrlMappingByOriginalUrl(String originalUrl) {
        return urlMappingRepository.findByOriginalUrl(originalUrl)
                .map(UrlMapping::toDto);
    }

    public Optional<UrlMapping> findUrlMappingByShortUrl(String shortUrl) {
        return urlMappingRepository.findByShortUrl(shortUrl)
                .map(UrlMapping::toDto);
    }

    public void saveUrlMapping(String originalUrl, String shortUrl) {
        urlMappingRepository.save(UrlMappingEntity.builder()
                .originalUrl(originalUrl)
                .shortUrl(shortUrl)
                .requestCount(1L)
                .build());
    }

    public void increaseRequestCount(String shortUrl) {
        UrlMappingEntity urlMappingEntity = getUrlMappingEntity(shortUrl);
        urlMappingEntity.incrementRequestCount();
    }

    public List<UrlMapping> getAllUrlMappings() {
        return urlMappingRepository.findAll().stream()
                .map(UrlMapping::toDto)
                .collect(Collectors.toList());
    }

    private UrlMappingEntity getUrlMappingEntity(String shortUrl) {
        return urlMappingRepository.findByShortUrl(shortUrl)
                .orElseThrow(ShortUrlNotFoundException::new);
    }

}
