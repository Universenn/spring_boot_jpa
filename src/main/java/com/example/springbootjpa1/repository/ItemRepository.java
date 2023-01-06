package com.example.springbootjpa1.repository;

import com.example.springbootjpa1.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            // merge 를 하면 준영속성 컨텍스트를 영속석 컨텍스트로 바꿔준다.
            // merge 는 병합 이여서 바뀌지 않는 값도 바꿔준다. (null)
            // 그래서 Entity 를 변경 할 때는 항상 merge 말고 변경감지를 사용해야한다.
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }



}
