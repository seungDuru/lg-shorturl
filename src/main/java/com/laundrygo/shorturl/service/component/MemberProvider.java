package com.laundrygo.shorturl.service.component;

import com.laundrygo.shorturl.common.exception.MemberNotFoundException;
import com.laundrygo.shorturl.repository.MemberRepository;
import com.laundrygo.shorturl.service.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class MemberProvider {

    private final MemberRepository memberRepository;

    public List<Member> findAllMembers() {
        return memberRepository.findAll().stream()
                .map(Member::from)
                .collect(Collectors.toList());
    }

    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .map(Member::from)
                .orElseThrow(MemberNotFoundException::new);
    }

}
