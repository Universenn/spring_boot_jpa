package com.example.springbootjpa1.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

//    @Column(nullable = false)
    @NotEmpty
    private String name;


    @Embedded // 내장 타입
    private Address address;

//    @JsonIgnore  // Db 요청 안보이게 해주는 어노테이션
    @OneToMany(mappedBy = "member") // mappedBy 를 사용함으로 써 읽기전용 이 된다.
    private List<Order> orders = new ArrayList<>();





}
