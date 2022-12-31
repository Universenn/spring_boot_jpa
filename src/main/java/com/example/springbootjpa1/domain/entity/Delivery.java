package com.example.springbootjpa1.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    // OneToOne 은 둘다 포링키(FK)를 둬도 되지만
    // order 에서 엑셉스 를 많이 하는 곳에다 두는걸 좋아한다.
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) // 거울
    private Order order;

    @Embedded
    private Address address;

    // Type 기본형 ORDINAL 이다 (숫자로 나타낸다. ex)1,2,3)
    // 문제가 다른 상태를 추가하면 안된다.
    // 꼭 String 으로 써줘야한다
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP

}
