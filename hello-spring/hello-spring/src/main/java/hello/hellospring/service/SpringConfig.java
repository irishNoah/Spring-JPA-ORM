package hello.hellospring.service;
import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.swing.*;

@Configuration
public class SpringConfig {
// ==========================================================

//    // ***** JPA 수업 이전까지 사용했던 코드 시작 *****
//    private final DataSource dataSource;
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//    // ***** JPA 수업 이전까지 사용했던 코드 끝 *****

// ==========================================================

//    // ***** 스프링 데이터 JPA 수업 이전까지 사용했던 코드 시작 *****
//    private final DataSource dataSource;
//    private final EntityManager em;
//
//    public SpringConfig(DataSource dataSource, EntityManager em){
//        this.dataSource = dataSource;
//        this.em = em;
//    }
//    // ***** 스프링 데이터 JPA 수업 이전까지 사용했던 코드 끝 *****

// ==========================================================

    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    // ==========================================================

//    // ***** 스프링 데이터 JPA 수업 이전까지 사용했던 코드 시작 *****
//    @Bean
//    public MemberRepository memberRepository() {
//        // return new MemoryMemberRepository(); // 순수 JDBC 가동 위해 주석 처리
//
//        //// ***** 순수 JDBC 시작 *****
//        // return new JdbcMemberRepository(dataSource);
//        //// ***** 순수 JDBC 끝 *****
//
//        //// ***** 스프링 JdbcTemplate 시작 *****
//        // return new JdbcTemplateMemberRepository(dataSource);
//        //// ***** 스프링 JdbcTemplate 끝 *****
//
//        //// ***** JPA 시작 *****
//        // return new JpaMemberRepository(em);
//        //// ***** JPA 끝 *****
//
//    }
//    // ***** 스프링 데이터 JPA 수업 이전까지 사용했던 코드 끝 *****

    // ==========================================================
    // 아래처럼 Bean 등록을 해도되지만, TimeTraceAop에서 컴포넌트 스캔으로 등록해서 필요 없음
    
//    // ***** AOP 적용 - 시간 측정 시작 *****
//    @Bean
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }
//    // ***** AOP 적용 - 시간 측정 끝 *****

    // ==========================================================
}