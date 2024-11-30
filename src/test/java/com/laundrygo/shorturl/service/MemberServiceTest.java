package com.laundrygo.shorturl.service;

import com.laundrygo.shorturl.common.exception.MemberNotFoundException;
import com.laundrygo.shorturl.domain.MemberEntity;
import com.laundrygo.shorturl.repository.MemberRepository;
import com.laundrygo.shorturl.service.component.MemberProvider;
import com.laundrygo.shorturl.service.dto.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberProvider memberProvider;

    @Test
    @DisplayName("모든 회원을 조회한다")
    void shouldFindAllMembers() {
        // given
        List<MemberEntity> entities = List.of(
                MemberEntity.builder().id(1L).name("홍길동").build(),
                MemberEntity.builder().id(2L).name("김철수").build()
        );
        given(memberRepository.findAll()).willReturn(entities);

        // when
        List<Member> result = memberProvider.findAllMembers();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("홍길동");
        then(memberRepository).should().findAll();
    }

    @Test
    @DisplayName("회원 ID로 회원 정보를 조회한다")
    void shouldFindMemberById() {
        // given
        Long memberId = 1L;
        MemberEntity entity = MemberEntity.builder().id(memberId).name("홍길동").build();
        given(memberRepository.findById(memberId)).willReturn(Optional.of(entity));

        // when
        Member result = memberProvider.findMemberById(memberId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("홍길동");
        then(memberRepository).should().findById(memberId);
    }

    @Test
    @DisplayName("존재하지 않는 회원 ID로 조회 시 예외를 발생시킨다")
    void shouldThrowExceptionWhenMemberNotFound() {
        // given
        Long memberId = 99L;
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> memberProvider.findMemberById(memberId))
                .isInstanceOf(MemberNotFoundException.class);
    }
}