package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity // JPA가 관리해줄 클래스[JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수]
public class Member { // 속성에 대해서 BaseEntity에서 상속 >>> 임베디드 타입 때는 사용 x(지저분해서)
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;

    /* 기간 : Period (임베디드 타입) */
    @Embedded // 값 타입을 정의하는 곳에 표시
    private Period workPeriod;

    /* 주소 : Address (임베디트 타입) */
    @Embedded // 값 타입을 정의하는 곳에 표시
    private Address homeAddress;
    /* 값 타입 컬렉션 */
    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();
//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns =
//    @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressHistory = new ArrayList<>();

    /* 값 타입 컬렉션 대안 */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();


    /* 객체 지향 모델링 >>> 객체의 참조와 테이블의 외래 키를 매핑 */
    @ManyToOne(fetch = FetchType.EAGER) /* 다대일 매핑, FetchType.EAGER >>> 즉시 로딩 */
    @JoinColumn(name = "TEAM_ID") /* 조인 컬럼은 외래 키를 매핑할 때 사용한다. (name 속성은 매핑할 외래 키 이름을 지정한다. */
    private Team team; // 연관관계 주인 ㅇ ==> 주인 아닌 놈은 Team.java에 있다.

    /* getter & setter */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}