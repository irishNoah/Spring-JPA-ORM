package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class jpaMain {
    public static void main(String[] args){
        //[엔티티 매니저 팩토리] - 생성
        final EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hello");
        //[엔티티 매니저] - 생성
        final EntityManager em = emf.createEntityManager();
        //[트랜잭션] - 획득
        final EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();     //[트랜잭션] - 시작
            logic(em);      //비즈니스 로직 실행
            tx.commit();    //[트랜잭션] - 커밋
        } catch (Exception e) {
            tx.rollback();  //[트랜잭션] - 롤백
            e.printStackTrace(); //추가
        } finally {
            em.close();     //[엔티티 매니저] - 종료
        }
        emf.close();        //[엔티티 매니저 팩토리] - 종료
    }

    //비즈니스 로직
    public static void logic(EntityManager em) {
        /* 로직 작성 */

        Member member = new Member();
        member.setUsername("member1");
        // 임베디드 값 타임
        member.setHomeAddress(new Address("homeCity", "street1", "12345"));

        // 기본값 타입 컬렉션
        member.getFavoriteFoods().add("치킨");
        member.getFavoriteFoods().add("족발");
        member.getFavoriteFoods().add("피자");

        // 임베디드 값 타입 컬렉션
        // 값 타입 저장
        member.getAddressHistory().add(new AddressEntity("old1", "street1", "12345"));
        member.getAddressHistory().add(new AddressEntity("old2", "street1", "12345"));

        em.persist(member);

        em.flush();
        em.clear();

        // SQL 쿼리 확인
        System.out.println("===============================");
        Member findMember = em.find(Member.class, member.getId());
        System.out.println("===============================");

//        /* 값 타입 수정 */
//        // 수정하고자 하는 값 homeCity >>> newCity
//
//        // findMember.getHomeAddress().setCity("newCity"); // >>> 이렇게 하면 안된다!!!!!
//
//        Address a = findMember.getHomeAddress();
//        // 아래와 같이 아예 갈아 끼어야 해!!!
//        findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));
//
//        // 음식 : 치킨 -> 한식
//        findMember.getFavoriteFoods().remove("치킨");
//        findMember.getFavoriteFoods().add("한식");
//
//        // 도시 : old1 > new1
//        findMember.getAddressHistory().remove(new Address("old1", "street1", "12345"));
//        findMember.getAddressHistory().add(new Address("new1", "street1", "12345"));
    }
}
