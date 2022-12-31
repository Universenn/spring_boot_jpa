package com.example.springbootjpa1.domain.item;

import com.example.springbootjpa1.domain.entity.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 전략 3 가지
// joined : 가장 정교화된 스타일로 하는 것
// single_table : 한 테이블에 다 넣는 것
// TABLE_PER_CLASS : 3개의 테이블만 나오는 전략?

// 상속관계 매핑이기 때문에
// 전략을 부모 클래스에 잡아줘야한다.
// single_table 을 사용 해 준다.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
@Entity
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;


    private String name;
    private int price;
    private int stockQuantity;

    // ManyToMany 는 JoinTable 이 필요하다
    // ManyToMany 실무에서 쓰지말라고 하는 이유
    // 필드 추가가 안된다. 단순하지 않다.
//    @ManyToMany
//    @JoinTable(name = "category_item",
//            joinColumns = @JoinColumn(name = "category_id"),
//            inverseJoinColumns =@JoinColumn(name = "item_id"))
    @ManyToMany(mappedBy = "items")
    private List<Category> categories =new ArrayList<>();


}
