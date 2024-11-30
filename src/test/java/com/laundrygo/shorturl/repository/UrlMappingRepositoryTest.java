package com.laundrygo.shorturl.repository;

import com.laundrygo.shorturl.domain.UrlMappingEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class UrlMappingRepositoryTest {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @AfterEach
    void tearDown() {
        urlMappingRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("원본 URL로 URL 매핑정보를 조회한다")
    void findByOriginalUrl() {
        // given
        String originalUrl = "originalUrl";
        String shortUrl = "shortUrl";
        UrlMappingEntity urlMappingEntity = buildUrlMappingEntity(originalUrl, shortUrl);

        urlMappingRepository.save(urlMappingEntity);

        // when
        UrlMappingEntity result = urlMappingRepository.findByOriginalUrl(originalUrl).get();

        // then
        assertThat(result)
                .extracting(UrlMappingEntity::getOriginalUrl, UrlMappingEntity::getShortUrl)
                .containsExactly(originalUrl, shortUrl);
    }

    @Test
    @DisplayName("단축 URL로 URL 매핑정보를 조회한다")
    void findByShortUrl() {
        // given
        String originalUrl = "originalUrl";
        String shortUrl = "shortUrl";
        UrlMappingEntity urlMappingEntity = buildUrlMappingEntity(originalUrl, shortUrl);

        urlMappingRepository.save(urlMappingEntity);

        // when
        UrlMappingEntity result = urlMappingRepository.findByShortUrl(shortUrl).get();

        // then
        assertThat(result)
                .extracting(UrlMappingEntity::getOriginalUrl, UrlMappingEntity::getShortUrl)
                .containsExactly(originalUrl, shortUrl);
    }

    private UrlMappingEntity buildUrlMappingEntity(String originalUrl, String shortUrl) {
        return UrlMappingEntity.builder()
                .originalUrl(originalUrl)
                .shortUrl(shortUrl)
                .requestCount(0L)
                .build();
    }
}