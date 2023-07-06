package hello.hellospring.reposiotry;


import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/*
테스트는 각각 독립적으로 실행되어야 한다. 테스트 순서에 의존관계가 있는 것은 좋은 테스트가 아니다.
*/

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    /*
     @AfterEach : 한번에 여러 테스트를 실행하면 메모리 DB에 직전 테스트의 결과가 남을 수 있다.
     이렇게 되면 다음 이전 테스트 때문에 다음 테스트가 실패할 가능성이 있다. @AfterEach 를 사용하면 각 테스트가
    종료될 때 마다 이 기능을 실행한다. 여기서는 메모리 DB에 저장된 데이터를 삭제한다.
    */

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("irish");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        //given
        Member member1 = new Member();
        member1.setName("study1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("study2");
        repository.save(member2);

        //when
        Member result = repository.findByName("study1").get();

        //then
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        //given
        Member member1 = new Member();
        member1.setName("check1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("check2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);

        // 아래 코드는 에러 남
        // >>> Member 객체의 개수는 2개인데, 3개 있냐고 물어봤으니깐!
        // assertThat(result.size()).isEqualTo(3);
    }

}