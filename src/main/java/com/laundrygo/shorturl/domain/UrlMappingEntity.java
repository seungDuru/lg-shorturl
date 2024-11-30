package com.laundrygo.shorturl.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "url_mapping")
@Entity
public class UrlMappingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String originalUrl;
    @Column(nullable = false)
    private String shortUrl;
    private Long requestCount;

    public void incrementRequestCount() {
        requestCount++;
    }

}
