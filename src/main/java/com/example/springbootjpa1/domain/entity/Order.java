package com.example.springbootjpa1.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 포링 키 member_id
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // persist 를 줄일 수 있다.?
    private List<OrderItems> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id") // 연관관계 주인
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
    }

    public void addOrderItem(OrderItems orderItems) {
        this.orderItems.add(orderItems);
        orderItems.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItems... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItems orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비지니스 로직==//

    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) { // deliveryStatus 가 comp면(배송 중)이면 취소가 불가능 하다
            throw new IllegalStateException("이미 배송완료된 상품은 취소 불가능 합니다.");
        }

        // 위 로직에 걸리지 않으면 cancel
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItems orderItem : orderItems) {
            orderItem.cancel();
        }
        
    }

    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItems orderItem : orderItems) {
            totalPrice += orderItem.getTotalRice();
        }
        return totalPrice;
    }

}
