package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
/*
PDF의 테이블 설계를 보면 ORDER이 아닌 ORDERS로 테이블명이 지어져 있음을 볼 수 있다.
왜냐하면 "ORDER"는 DB에서 사용되는 SQL 키워드 중 한 가지이기 때문이다.
어떤 DB에서는 "ORDER" 자체를 테이블명으로 사용되도 문제 없지만,
특정 DB에서는 문제가 될 수 있으므로, 이런 문제를 미리 대응하고자
Order 클래스에 대해 @Table(name = "ORDERS") 로 처리하는 게 좋다.
 */
@Table(name = "ORDERS")
public class Order extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID") // 테이블 설계 참고
    private Long id;

    /* 연관관계 매핑 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();


    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // Orinal로 하면 추후에 순서가 꼬일 경우, 큰 장애가 발생할 수 있음
    private OrderStatus status;

    /* 양방향 관계가 걸리도록 설정 */
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }


    /* getter와 setter 부분은 위의 필드값 변경에 따라 맞게 계속 수정해줘야 한다. */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
