package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends BaseEntity { // 속성에 대해서 BaseEntity에서 상속

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    /*
    mappedBy 속성은 양방향 매핑일 때 사용한다. 이때 mappedBy의 값은 반대쪽 매핑 객체의 필드명이다.
    즉, member.java에서 "private Team team;" 으로 선언한 이 필드명(team)을 의미하는 것이다.
    */
    @OneToMany(mappedBy = "team") // 연관관계 주인 X ==> 주인은 member.java에 있음
    private List<Member> members = new ArrayList<>(); // 관례상 널 포인터를 만들지 않기 위해 이렇게 초기화

    /* getter & setter 부분 */
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public List<Member> getMembers() {return members;}

    public void setMembers(List<Member> members) {this.members = members;}


}
