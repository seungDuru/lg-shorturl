package com.laundrygo.shorturl.service.dto;

import com.laundrygo.shorturl.domain.MemberEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {

    private Long id;
    private String name;

    public static Member from(MemberEntity entity) {
        return Member.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

}
