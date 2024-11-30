package com.laundrygo.shorturl.repository;

import com.laundrygo.shorturl.domain.UrlMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMappingEntity, Long> {

    Optional<UrlMappingEntity> findByOriginalUrl(String originalUrl);

    Optional<UrlMappingEntity> findByShortUrl(String shortUrl);

}
