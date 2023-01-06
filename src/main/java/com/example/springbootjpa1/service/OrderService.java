package com.example.springbootjpa1.service;

import com.example.springbootjpa1.domain.entity.Delivery;
import com.example.springbootjpa1.domain.entity.Member;
import com.example.springbootjpa1.domain.entity.Order;
import com.example.springbootjpa1.domain.entity.OrderItems;
import com.example.springbootjpa1.domain.item.Item;
import com.example.springbootjpa1.repository.ItemRepository;
import com.example.springbootjpa1.repository.MemberRepository;
import com.example.springbootjpa1.repository.OrderRepository;
import com.example.springbootjpa1.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // entity 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송 정보
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItems orderItem = OrderItems.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
