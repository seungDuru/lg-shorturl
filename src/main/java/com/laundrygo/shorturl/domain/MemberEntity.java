package com.laundrygo.shorturl.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author laundrygo
 * @version 0.1.0
 * @since 2021/06/22 7:01 오후
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

}
