package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // JPA는 EntityManager라는 것으로 모든 것을 돈다.
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        /*
        em.persist(member)를 통해
        setID도 해주고, insert도 해준다.

        >>> 얼마나 편안한가!
         */
        em.persist(member); // persist() : 영속
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        /*
        여태껏 테이블 대상으로 쿼리를 날렸는데,
        여기서는 객체를 대상으로 쿼리를 날림
         */
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
