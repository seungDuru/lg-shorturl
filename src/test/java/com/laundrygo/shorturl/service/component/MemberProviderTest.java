package com.laundrygo.shorturl.service.component;

import com.laundrygo.shorturl.common.exception.MemberNotFoundException;
import com.laundrygo.shorturl.domain.MemberEntity;
import com.laundrygo.shorturl.repository.MemberRepository;
import com.laundrygo.shorturl.service.dto.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberProviderTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberProvider memberProvider;


    @Test
    @DisplayName("모든 회원을 조회한다")
    void shouldFindAllMembers() {
        // given
        List<MemberEntity> entities = List.of(
                buildMember("홍길동"),
                buildMember("이순신"));
        given(memberRepository.findAll()).willReturn(entities);

        // when
        List<Member> result = memberProvider.findAllMembers();

        // then
        assertThat(result).hasSize(2)
                .extracting(Member::getName)
                .containsExactly("홍길동", "이순신");
    }

    @Test
    @DisplayName("ID로 회원을 조회한다")
    void shouldFindMemberById() {
        // given
        Long memberId = 1L;
        MemberEntity entity = buildMember("홍길동");
        given(memberRepository.findById(memberId)).willReturn(Optional.of(entity));

        // when
        Member result = memberProvider.findMemberById(memberId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("존재하지 않는 ID로 회원을 조회하면 예외를 발생시킨다")
    void shouldThrowExceptionWhenMemberNotFound() {
        // given
        Long memberId = 99L;
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> memberProvider.findMemberById(memberId))
                .isInstanceOf(MemberNotFoundException.class);
    }

    private MemberEntity buildMember(String name) {
        return MemberEntity.builder()
                .name(name)
                .build();
    }
}