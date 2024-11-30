package com.laundrygo.shorturl.service.component;

import com.laundrygo.shorturl.common.exception.ShortUrlNotFoundException;
import com.laundrygo.shorturl.domain.UrlMappingEntity;
import com.laundrygo.shorturl.repository.UrlMappingRepository;
import com.laundrygo.shorturl.service.dto.UrlMapping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UrlMappingProviderTest {

    @Mock
    private UrlMappingRepository urlMappingRepository;

    @InjectMocks
    private UrlMappingProvider urlMappingProvider;

    @Test
    @DisplayName("원본 URL로 URL 매핑을 조회한다")
    void shouldFindUrlMappingByOriginalUrl() {
        // given
        String originalUrl = "http://example.com";
        UrlMappingEntity mockEntity = UrlMappingEntity.builder()
                .originalUrl(originalUrl)
                .shortUrl("abc123")
                .requestCount(10L)
                .build();
        given(urlMappingRepository.findByOriginalUrl(originalUrl)).willReturn(Optional.of(mockEntity));

        // when
        Optional<UrlMapping> result = urlMappingProvider.findUrlMappingByOriginalUrl(originalUrl);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getShortUrl()).isEqualTo("abc123");
        assertThat(result.get().getRequestCount()).isEqualTo(10L);
    }

    @Test
    @DisplayName("단축 URL로 URL 매핑을 조회한다")
    void shouldFindUrlMappingByShortUrl() {
        // given
        String shortUrl = "abc123";
        UrlMappingEntity mockEntity = UrlMappingEntity.builder()
                .originalUrl("http://example.com")
                .shortUrl(shortUrl)
                .requestCount(10L)
                .build();
        given(urlMappingRepository.findByShortUrl(shortUrl)).willReturn(Optional.of(mockEntity));

        // when
        Optional<UrlMapping> result = urlMappingProvider.findUrlMappingByShortUrl(shortUrl);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getOriginalUrl()).isEqualTo("http://example.com");
        assertThat(result.get().getRequestCount()).isEqualTo(10L);
    }

    @Test
    @DisplayName("URL 매핑 정보를 저장한다")
    void shouldSaveUrlMapping() {
        // given
        String originalUrl = "http://example.com";
        String shortUrl = "abc123";
        UrlMappingEntity savedEntity = UrlMappingEntity.builder()
                .originalUrl(originalUrl)
                .shortUrl(shortUrl)
                .requestCount(1L)
                .build();
        given(urlMappingRepository.save(any(UrlMappingEntity.class))).willReturn(savedEntity);

        // when
        urlMappingProvider.saveUrlMapping(originalUrl, shortUrl);

        // then
        then(urlMappingRepository).should().save(any(UrlMappingEntity.class));
    }

    @Test
    @DisplayName("단축 URL의 요청 수를 증가시킨다")
    void shouldIncreaseRequestCount() {
        // given
        String shortUrl = "abc123";
        UrlMappingEntity mockEntity = UrlMappingEntity.builder()
                .originalUrl("http://example.com")
                .shortUrl(shortUrl)
                .requestCount(5L)
                .build();
        given(urlMappingRepository.findByShortUrl(shortUrl)).willReturn(Optional.of(mockEntity));

        // when
        urlMappingProvider.increaseRequestCount(shortUrl);

        // then
        assertThat(mockEntity.getRequestCount()).isEqualTo(6L);
        then(urlMappingRepository).should().findByShortUrl(shortUrl);
    }

    @Test
    @DisplayName("모든 URL 매핑을 조회한다")
    void shouldGetAllUrlMappings() {
        // given
        List<UrlMappingEntity> mockEntities = List.of(
                UrlMappingEntity.builder()
                        .originalUrl("http://example.com")
                        .shortUrl("abc123")
                        .requestCount(10L)
                        .build(),
                UrlMappingEntity.builder()
                        .originalUrl("http://example.org")
                        .shortUrl("def456")
                        .requestCount(5L)
                        .build()
        );
        given(urlMappingRepository.findAll()).willReturn(mockEntities);

        // when
        List<UrlMapping> result = urlMappingProvider.getAllUrlMappings();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getShortUrl()).isEqualTo("abc123");
        assertThat(result.get(1).getShortUrl()).isEqualTo("def456");
    }

    @Test
    @DisplayName("단축 URL로 URL 매핑을 조회하지 못하면 예외를 발생시킨다")
    void shouldThrowExceptionWhenShortUrlNotFound() {
        // given
        String shortUrl = "notExist123";
        given(urlMappingRepository.findByShortUrl(shortUrl)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> urlMappingProvider.increaseRequestCount(shortUrl))
                .isInstanceOf(ShortUrlNotFoundException.class);
    }
}