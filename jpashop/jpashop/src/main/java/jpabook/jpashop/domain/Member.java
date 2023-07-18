package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue // strategy를 따로 설정 안하면 AUTO 자동 설정
    @Column(name = "MEMBER_ID") // 테이블 설계 참고
    private Long id;
    private String name;

    @Embedded
    private Address address;

    /* 참조를 사용하도록 변경 */
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order)
    {
        orders.add(order);
        order.setMember(this);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
