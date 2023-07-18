package hellojpa;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 상속 관계 > 조인 전략
@DiscriminatorColumn // 저장된 자식 테이블을 DTYPE에 따라 구분 & 단일 테이블과 구현 클래스 전략에서는 사용 안해도 됨(알아서, DTYPE 생성)
public abstract class Item { // 고급 매핑 // Item 클래스는 추상화 해줘야 함 >>> abstract 없으면 독단적으로도 사용할 수도 있단 말
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

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
}
