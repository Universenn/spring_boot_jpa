package com.example.springbootjpa1.service;

import com.example.springbootjpa1.domain.entity.Member;
import com.example.springbootjpa1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final 이 걸어진 필드를 가지고 생성자를 만들어준다.
public class MemberService {

    // final 를 적지 않으면 컴파일 에러가 난다.
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
//    @Transactional(readOnly = false) 기본값이 false
    @Transactional
    public Long join(Member member) {
        // 중복회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // Exception
        // 동시에 하면 이 예외처리를 통과한다. 고로 두명이 회원가입이 된다. 그래서 실무에서는 memberName 같은 경우를 유니크를 걸어 준다.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 상세 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }



}
