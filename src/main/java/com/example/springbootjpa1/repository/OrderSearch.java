package com.example.springbootjpa1.repository;

import com.example.springbootjpa1.domain.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus; // 주문 상태[ORDER, CANCEL]
}
