package com.example.springbootjpa1.domain.entity;

import com.example.springbootjpa1.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItems {

    @Id @GeneratedValue
    @Column(name = "order_items_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    //==생성 메서드==//
    public static OrderItems createOrderItem(Item item, int orderPrice, int count) {
        OrderItems orderItems = new OrderItems();
        orderItems.setItem(item);
        orderItems.setOrderPrice(orderPrice);
        orderItems.setCount(count);

        // 재고 차감
        item.removeStock(count);
        return orderItems;
    }

    //==비지니스 로직==//
    public void cancel() {
        // 재고 수량 원복을 해준다.
        getItem().addStock(count);
    }

    //==조회 로직==//

    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalRice() {
        return getOrderPrice() * getCount();
    }
}
