package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 고급[상속관계] 매핑 > 싱글 테이블 전략 선택
@DiscriminatorColumn // 디폴트 name은 DTYPE
public abstract class Item extends BaseEntity { // 단독 테이블 사용을 하지 않는다고 가정하고 abstract 시켜서 추상화

    @Id @GeneratedValue
    @Column(name = "ITEM_ID") // 테이블 설계 참고
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
