package com.example.springbootjpa1.service;

import com.example.springbootjpa1.domain.entity.Address;
import com.example.springbootjpa1.domain.entity.Member;
import com.example.springbootjpa1.domain.entity.Order;
import com.example.springbootjpa1.domain.entity.OrderStatus;
import com.example.springbootjpa1.domain.item.Book;
import com.example.springbootjpa1.domain.item.Item;
import com.example.springbootjpa1.exception.NotEnoughStockException;
import com.example.springbootjpa1.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    private Book getBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);  // 수량
        em.persist(book);
        return book;
    }

    private Member getMember() {
        Member member = new Member();
        member.setUserName("woo");
        member.setAddress(new Address("서울", "특별시", "강동구"));
        em.persist(member);
        return member;
    }

    @Test
    public void 상품주문() {
        // given
        Member member = getMember();

        Book book = getBook("JPA ORM 표준", 10000, 10);

        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);


        // then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("주문한 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
        Assert.assertEquals("주문 수량 만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() {
        // given
        Member member = getMember();
        Item item = getBook("JPA ORM 표준", 10000, 10);

//        int orderCount = 10; // no 에러 처리
        int orderCount = 11;
        // when
        orderService.order(member.getId(), item.getId(), orderCount);

        // then
        fail("재고 수량 부족 예외 발생해야 함");
    }



    @Test
    public void 주문취소() {
        // given
        Member member = getMember();
        Book book = getBook("JPA ORM 표준", 10000, 10);

        int orderCount = 2;
        Long orderId= orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);

        // then
        Assert.assertEquals("상품 주문 시 상태는 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        Assert.assertEquals("주문한 상품 갯수가 정확해야 한다.", 2, getOrder.getTotalPrice()/ book.getPrice());
        Assert.assertEquals("주문 취소 수량 만큼 재고가 남아 있어야 한다.", 10, book.getStockQuantity());


    }

}