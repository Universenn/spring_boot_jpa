package com.example.springbootjpa1.repository;

import com.example.springbootjpa1.domain.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    // EntityManager 주입
    private EntityManager em;

    public Long save(Member member) {
        // 저장
        em.persist(member);
        // 아이디 값 리턴
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
