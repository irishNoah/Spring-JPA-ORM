package jpabook.jpashop;

import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
        } finally {
            em.close();     //[엔티티 매니저] - 종료
        }
        emf.close();        //[엔티티 매니저 팩토리] - 종료
    }

    //비즈니스 로직
    public static void logic(EntityManager em) {
        /* 로직 작성 */

        Book book = new Book();
        book.setName("helloLever");
        book.setAuthor("박창영");
        book.setPrice(23000);

        em.persist(book);

    }



}