package com.laundrygo.shorturl.service;

import com.laundrygo.shorturl.domain.MemberEntity;
import com.laundrygo.shorturl.repository.MemberRepository;
import com.laundrygo.shorturl.service.component.MemberProvider;
import com.laundrygo.shorturl.service.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author laundrygo
 * @version 0.1.0
 * @since 2021/06/22 7:06 오후
 */
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberProvider memberProvider;

    public List<Member> findAllMembers() {
        return memberProvider.findAllMembers();
    }

    public Member findMemberById(Long memberId) {
        return memberProvider.findMemberById(memberId);
    }

}
