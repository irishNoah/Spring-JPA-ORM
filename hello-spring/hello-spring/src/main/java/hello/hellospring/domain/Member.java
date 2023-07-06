package hello.hellospring.domain;


import javax.persistence.Entity; // JPA로 인해 추가
import javax.persistence.GeneratedValue; // JPA로 인해 추가
import javax.persistence.GenerationType; // JPA로 인해 추가
import javax.persistence.Id; // JPA로 인해 추가

@Entity // JPA로 인해 추가
public class Member {
    /*
    JPA로 인해 추가

    @ID
    > 테이블의 PK

    @ @GeneratedValue(strategy = GenerationType.IDENTITY)
    > 아이덴티티 전략을 위해 추가
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // getter & setter 생성
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
}
