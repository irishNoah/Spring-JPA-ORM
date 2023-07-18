package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class OrderItem extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID") // 테이블 설계 참고
    private Long id;

    /* 참조를 사용하도록 변경 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    /* 참조를 사용하도록 변경 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice;
    private int count;

    /* getter와 setter 부분은 위의 필드값 변경에 따라 맞게 계속 수정해줘야 한다. */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
