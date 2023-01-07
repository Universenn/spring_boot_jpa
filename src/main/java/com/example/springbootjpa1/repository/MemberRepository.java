package com.example.springbootjpa1.repository;

import com.example.springbootjpa1.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {


    // EntityManager 주입
    private final EntityManager em;

    public Long save(Member member) {
        // 저장
        em.persist(member);
        // 아이디 값 리턴
        return member.getId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        List<Member> result =em.createQuery("select m from Member m", Member.class) // jpql Entity 객체를 대상으로 쿼리를 한다.
                .getResultList();
        return result;
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
