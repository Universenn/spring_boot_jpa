package com.example.springbootjpa1.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userName;


    @Embedded // 내장 타입
    private Address address;

    @OneToMany(mappedBy = "member") // mappedBy 를 사용함으로 써 읽기전용 이 된다.
    private List<Order> orders = new ArrayList<>();





}
