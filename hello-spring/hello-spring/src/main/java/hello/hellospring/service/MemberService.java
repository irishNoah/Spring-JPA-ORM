package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // JPA로 인해 사용

import java.util.List;
import java.util.Optional;

@Transactional // JPA로 인해 사용
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

// ==============================================================================
    /*
    회원가입
     */
    public Long join(Member member){
            validateDuplicateMember(member); // 중복 회원 검증 --> 중복된 게 없으면 아래 코드 실행
            memberRepository.save(member);
            return member.getId();
    }

    /*
    // "AOP가 필요한 상황" 강의에서 사용, "AOP 적용" 강의 때 기존으로 돌아감

    public Long join(Member member){
        long start = System.currentTimeMillis();

        try{
            validateDuplicateMember(member); // 중복 회원 검증 --> 중복된 게 없으면 아래 코드 실행
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish-start;
            System.out.println("join " + timeMs + "ms");
        }
    }
     */
// ==============================================================================

    /*
    회원가입시 이름이 동일한 회원이 존재하는지 유뮤 판단하는 함수
    즉, 중복된 이름이 존재해서는 안됨
     */
    private void validateDuplicateMember(Member member){
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

// ==============================================================================
    /*
    전체 회원 조회
     */
    public List<Member> findMembers(){
            return memberRepository.findAll();
    }

    /*
    // "AOP가 필요한 상황" 강의에서 사용, "AOP 적용" 강의 때 기존으로 돌아감

    public List<Member> findMembers(){
        long start = System.currentTimeMillis();

        try{
            return memberRepository.findAll();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish-start;
            System.out.println("findMembers = " + timeMs + "ms");
        }
    }
     */
// ==============================================================================
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
