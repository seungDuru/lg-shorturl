package com.laundrygo.shorturl.controller;

import com.laundrygo.shorturl.common.advice.ApiResponse;
import com.laundrygo.shorturl.service.MemberService;
import com.laundrygo.shorturl.service.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author laundrygo
 * @version 0.1.0
 * @since 2021/06/22 7:05 오후
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members")
    public ApiResponse<List<Member>> findAllMembers() {
        return ApiResponse.success(memberService.findAllMembers());
    }

    @GetMapping("/members/{memberId}")
    public ApiResponse<Member> findMemberById(@PathVariable Long memberId) {
        return ApiResponse.success(memberService.findMemberById(memberId));
    }

}
