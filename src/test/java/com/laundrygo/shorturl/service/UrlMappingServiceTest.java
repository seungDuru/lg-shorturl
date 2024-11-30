package com.laundrygo.shorturl.service;

import com.laundrygo.shorturl.common.exception.ShortUrlNotFoundException;
import com.laundrygo.shorturl.service.component.UrlMappingProvider;
import com.laundrygo.shorturl.service.dto.UrlMapping;
import com.laundrygo.shorturl.service.response.SavedOriginalUrlResponse;
import com.laundrygo.shorturl.service.response.SavedShortUrlResponse;
import com.laundrygo.shorturl.service.response.UrlMappingsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UrlMappingServiceTest {
    @Mock
    private UrlMappingProvider urlMappingProvider;

    @InjectMocks
    private UrlMappingService urlMappingService;

    @Test
    @DisplayName("새로운 원본 URL로 단축 URL을 생성하고 저장한다")
    void shouldGenerateShortUrlForNewOriginalUrl() {
        // given
        String originalUrl = "http://example.com";
        String generatedShortUrl = "abc123";

        given(urlMappingProvider.findUrlMappingByOriginalUrl(originalUrl)).willReturn(Optional.empty());
        given(urlMappingProvider.findUrlMappingByShortUrl(anyString())).willReturn(Optional.empty());

        // when
        SavedShortUrlResponse result = urlMappingService.generateShortUrl(originalUrl);

        // then
        assertThat(result.getShortUrl()).isNotNull();
        then(urlMappingProvider).should().saveUrlMapping(eq(originalUrl), anyString());
    }

    @Test
    @DisplayName("기존 원본 URL에 대한 단축 URL 생성 요청 시 요청 수를 증가시킨다")
    void shouldIncreaseRequestCountForExistingOriginalUrl() {
        // given
        String originalUrl = "http://example.com";
        String shortUrl = "abc123";
        UrlMapping mockMapping = UrlMapping.builder()
                .originalUrl(originalUrl)
                .shortUrl(shortUrl)
                .requestCount(1L)
                .build();

        given(urlMappingProvider.findUrlMappingByOriginalUrl(originalUrl)).willReturn(Optional.of(mockMapping));

        // when
        SavedShortUrlResponse result = urlMappingService.generateShortUrl(originalUrl);

        // then
        assertThat(result.getShortUrl()).isEqualTo(shortUrl);
        then(urlMappingProvider).should().increaseRequestCount(shortUrl);
    }

    @Test
    @DisplayName("단축 URL로 원본 URL을 조회한다")
    void shouldGetOriginalUrlByShortUrl() {
        // given
        String shortUrl = "abc123";
        String originalUrl = "http://example.com";
        UrlMapping mockMapping = UrlMapping.builder()
                .originalUrl(originalUrl)
                .shortUrl(shortUrl)
                .requestCount(1L)
                .build();

        given(urlMappingProvider.findUrlMappingByShortUrl(shortUrl)).willReturn(Optional.of(mockMapping));

        // when
        SavedOriginalUrlResponse result = urlMappingService.getOriginalUrl(shortUrl);

        // then
        assertThat(result.getOriginalUrl()).isEqualTo(originalUrl);
    }

    @Test
    @DisplayName("존재하지 않는 단축 URL로 원본 URL 조회 시 예외를 발생시킨다")
    void shouldThrowExceptionWhenShortUrlNotFound() {
        // given
        String shortUrl = "notExist123";
        given(urlMappingProvider.findUrlMappingByShortUrl(shortUrl)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> urlMappingService.getOriginalUrl(shortUrl))
                .isInstanceOf(ShortUrlNotFoundException.class);
    }

    @Test
    @DisplayName("저장된 모든 URL 매핑 정보를 조회한다")
    void shouldGetAllUrlMappings() {
        // given
        List<UrlMapping> mockMappings = List.of(
                UrlMapping.builder()
                        .originalUrl("http://example.com")
                        .shortUrl("abc123")
                        .requestCount(10L)
                        .build(),
                UrlMapping.builder()
                        .originalUrl("http://example.org")
                        .shortUrl("def456")
                        .requestCount(5L)
                        .build()
        );

        given(urlMappingProvider.getAllUrlMappings()).willReturn(mockMappings);

        // when
        UrlMappingsResponse result = urlMappingService.getAllUrlMappings();

        // then
        assertThat(result.getUrlMappings()).hasSize(2);
        assertThat(result.getUrlMappings().get(0).getShortUrl()).isEqualTo("abc123");
    }

    @Test
    @DisplayName("단축 URL 생성 시 중복되지 않는 고유한 값을 생성한다")
    void shouldGenerateUniqueShortUrl() {
        // given
        String originalUrl = "http://example.com";
        String uniqueShortUrl = "unique123";

        given(urlMappingProvider.findUrlMappingByOriginalUrl(originalUrl)).willReturn(Optional.empty());
        given(urlMappingProvider.findUrlMappingByShortUrl(anyString())).willReturn(Optional.empty());

        // when
        SavedShortUrlResponse result = urlMappingService.generateShortUrl(originalUrl);

        // then
        assertThat(result.getShortUrl()).isNotNull();
        then(urlMappingProvider).should().saveUrlMapping(eq(originalUrl), anyString());
    }

}