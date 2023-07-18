> 총정리(스프링 입문)

# 섹터 1

## JUnit 이란?

- 자바 테스트 프레임워크
- 보이지 않고 숨겨진 단위 테스트를 끌어내어 정형화시켜 단위 테스트를 쉽게 해주는 테스트용 프레임워크로 쉽게 말해 테스트 도구이다.

## thymeleaf란?

- 컨트롤러가 전달하는 데이터를 이용해 동적으로 화면을 만들어주는 역할을 하는 뷰 템플릿 엔진

## 스프링부트 동작 환경 그림 및 설명
![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/e557d73b-270a-4bc0-b58a-f28edc8981e7)



- Controller
    - HelloController.java에서 return을 “hello”로 했다.
        - HelloController.java 경로
            - [https://github.com/irishNoah/Spring-JPA-ORM/blob/main/hello-spring/hello-spring/src/main/java/hello/hellospring/controller/HelloController.java](https://github.com/irishNoah/Spring-JPA-ORM/blob/main/hello-spring/hello-spring/src/main/java/hello/hellospring/controller/HelloController.java)
    - 그렇게 될 경우 resources/templates에서 “hello”와 동일한 html 파일을 찾아 실행하게 된다.
- 뷰 리졸버( viewResolver )
    - java 파일에 있는 atrributeValue 값을 찾아 html 파일의 ${data}에 전송

---

# 섹터 2

## 정적 컨텐츠

### 소스 코드 생성 및 실행

- resources/static 파일에 /hello-static.html 생성

```html
<!DOCTYPE HTML>
<html>
<head>
 <title>static content</title>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
정적 컨텐츠 입니다.
</body>
</html>
```

- 위 코드를 실행

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/e19ed743-a770-4e66-b7d7-78a2252de80b)

들어가보면

- 위 화면과 같이 잘 실행됨

### 스프링 부트의 정적 컨텐츠 설명

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(%E1%8.png)

- 실행하면 내장 톰켓 서버로 이동 후 스프링 컨테이너에 정적 html이 있는지 확인한다.
- 여기서 hello-static.html은 컨트롤러가 없으므로 2번에 해당하는 resources에서 hello-static.html이 있는지 확인한다.
- resources에서 해당 파일이 있으므로 이 정적 컨텐츠를 웹 브라우저에 반환한다.
- 정리
    - 컨테이너에 html과 관련된 컨트롤러가 있는지 확인
        - 있으면, 뷰 리졸버를 통해 반환
        - 없으면, resources에 있는지 확인하여 있으면 정적 컨텐츠를 웹 브라우저에 반환

## MVC와 템플릿 엔진

### MVC란?

- Model
    - 모델은 애플리케이션의 데이터 및 비즈니스 로직을 나타냅니다.
- View
    - View는 애플리케이션의 사용자 인터페이스를 나타냅니다.
- Controller
    - 컨트롤러는 모델과 뷰 사이의 중개자 역할을 합니다.
- MVC로 분리되는 이유
    - MVC의 핵심 아이디어는 애플리케이션의 데이터 및 로직과 사용자 인터페이스를 분리하는 것

### MVC와 템플릿 엔진의 원리 설명

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/07f4d4e2-c8fa-4136-9192-f2f759ce5b9d)

- 컨트롤러에 “hello-mvc”를 @GetMapping 했다.
    - 그리고 return을 hello-template로 했다.
- hello-template에 'hello ' + ${name}을 작성했다.
    - 즉 Get / Post를 이용해 name 값이 달라질 수 있는 것!
- 위 이미지에 나온 것처럼 [localhost:8080/hello-mvc를](http://localhost:8080/hello-mvc를) 치면 내장 톰캣 서버에서 컨트롤러를 통해 hello-mvc를 확인하고, hello-mvc의 리턴값이 “hello-template”이므로 이 html에 name 값을 전달
    - 만약 name을 spring으로 했다면 화면상에서 “hello spring”이 되는 것!
    - 만약 name을 irish로 했다면 화면상에서 “hello irish”가 되는 것!

### 실행 화면

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/02657001-2032-47b3-ba13-95382c083cc9)

- URL 창에서 hello-mvc 뒤에 ?name=irish 하고 엔터를 치니까 실행 화면에 “hello irish”란 창이 뜬 것을 볼 수 있다.

![U6048eff/Untitled%205.png)

- 또한 실행 코드를 보면 'hello ' + ${name}의 ${name} 자리에 irish가 들어있는 것을 알 수 있다.
- 즉, MVC는 html 형식으로 데이터가 오고 간다!!!

## API로 데이터 주고 받기

### @ResponseBody / @RequestBody

- @ResponseBody를 공부하는 김에 @RequestBody도 함께 공부하자

<aside>
💡 @RequestBody / @ResponseBody 정리.

클라이언트에서 서버로 필요한 데이터를 요청하기 위해 JSON 데이터를 요청 본문에 담아서 서버로 보내면, 서버에서는 @RequestBody 어노테이션을 사용하여 HTTP 요청 본문에 담긴 값들을 자바 객체로 변환시켜, 객체에 저장한다.

서버에서 클라이언트로 응답 데이터를 전송하기 위해 @ResponseBody 어노테이션을 사용하여 자바 객체를 HTTP 응답 본문의 객체로 변환하여 클라이언트로 전송한다.

</aside>

## 실행 화면

### @ResponseBody 문자 반환

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/f6d7f965-87c2-4872-ad20-c9983a8eb100)

### @ResponseBody 객체 반환

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/14906193-8f79-48cb-87b9-20a78cf39095)

- @ResponseBody 문자 반환과 @ResponseBody 객체 반환를 보면서 어떤 것을 느낄 수 있을까?
    - API는 html이 존재하지 않고 string값이나 Key-Value 값으로 이루어진다!!!
        - 즉, MVC는 html 기반 / API는 string 또는 Key-Value 값 기반으로 데이터가 오고 간다!
    - 페이지 소스를 보면 html 코드들이 없다는 것이다.
    - 즉, 이를 통해 API를 통한 요청은 HTTP의 BODY에 문자 내용을 직접 반환한다는 것을 알 수 있다!!!

## @ResponseBody 사용 원리

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/60c5ad37-1125-460e-ba41-4bb29ba55086)


- *HttpMessageConverter 용도*
    - • HTTP API처럼 JSON 데이터를 HTTP 메시지 바디 내 직접 읽거나 쓰는 경우 사용
    - [https://jaimemin.tistory.com/1823](https://jaimemin.tistory.com/1823)

---

# 섹터 3

## 일반적인 웹 애플리케이션 계층 구조

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/cedba407-c8d7-4a69-9017-16941df68ea1)


- 서비스에서 핵심 비즈니스 로직을 구현
    - 예를 들면, 회원 ID가 중복되면 안된다는 로직을 구현하는 것 등이 있음

## Optional

```java
package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
```

- Optional
    - Java 8에서 추가
    - Optional이 붙어있는 해당 메서드를 호출했을 때, 값이 null일 경우 null을 그대로 반환하는 대신에 Optional을 통해서 null을 처리하는 방법
    - 이게 나오기 전까지는 if 문을 해서 이게 null이냐를 판별했어야 했다.
        - 하지만, 이게 나오고서는 더 간단하게 코드를 짤 수 있게 됐다.

## 테스트 케이스

- 테스트 케이스를 통해서 하면 전반적인 테스트 케이스를 확인할 수 있다.
- 이를 JUnit이 가능하게 해준다.

## @BeforeEach

- @BeforeEach : 각 테스트 실행 전에 호출된다. 테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고, 의존관계도 새로 맺어준다. > DI 주입

## @AfterEach

- 한번에 여러 테스트를 실행하면 메모리 DB에 직전 테스트의 결과가 남을 수 있다. 이렇게 되면 이전 테스트 때문에 다음 테스트가 실패할 가능성이 있다.
- @AfterEach 를 사용하면 각 테스트가 종료될 때 마다 메모리 DB에 저장된 데이터를 삭제한다.
- 테스트는 각각 독립적으로 실행되어야 한다. 테스트 순서에 의존관계가 있는 것은 좋은 테스트가 아니다. 그래서 @AfterEach를 사용하는 것이다.

---

# 섹션 4

## 컴포넌트 스캔과 자동 의존관계 설정

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/aca44499-1a74-4f02-9880-e35c7e223980)


- 컨트롤러를 통해 서비스를 얻고자 하는 상황
- 서비스를 제공하려면 저장되어 있는 멤버를 파악 후 제공해야 하므로, 서비스는 레포지터리를 찾아야 한다.
- 해당 프로그램에서는 컨트롤러가 ‘memberController’ / 서비스가 ‘memberService’ / 레포지터리가 ‘memberRepository’로 이름 지어져 있다.
- 즉, memberController -> memberService -> memberRepository로 이루어져 있는 상황!

## 회원 컨트롤러(MemberController)에 의존관계 추가

```java
package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    // 회원 컨트롤러가 회원서비스와 회원 리포지토리를 사용할 수 있게 의존관계를 준비하자.

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        // 여기서는 MemberService에 DI를 해주었다.
        this.memberService = memberService;
    }
}
```

### @Autowired

- 스프링 DI(Dependency Injection)에서 사용되는 어노테이션입니다.
    - 스프링에서 빈 인스턴스가 생성된 이후 @Autowired를 설정한 메서드가 자동으로 호출되고, 인스턴스가 자동으로 주입됩니다.
- 생성자에 @Autowired 가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어준다. 이렇게 객체 의존관계를 외부에서 넣어주는 것을 DI (Dependency Injection), 의존성 주입이라 한다.
- 이전 테스트에서는 개발자가 직접 주입했고, 여기서는 @Autowired에 의해 스프링이 주입해준다
- 위와 같은 원리로 서비스와 리포지토리에도 스프링 빈을 등록해야 한다!

### 회원 서비스 스프링 빈 등록

```java
@Service
public class MemberService {

 private final MemberRepository memberRepository;

 @Autowired
 public MemberService(MemberRepository memberRepository) {
	 this.memberRepository = memberRepository;
 }
.
.
.
.
.
.
}
```

- 참고: 생성자에 @Autowired 를 사용하면 객체 생성 시점에 스프링 컨테이너에서 해당 스프링 빈을 찾아서 주입한다.
- 생성자가 1개만 있으면 @Autowired 는 생략할 수 있다.

### 회원 리포지토리 스프링 빈 등록

```java
@Repository
public class MemoryMemberRepository implements MemberRepository {...}
```

## 스프링 빈 등록 이미지

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/991cae68-a82d-4d66-ac79-e63b56fb1096)


- 위에 있는 스프링 빈 등록 과정을 거쳐 위와 같은 상황을 이룰 수 있게 됐다.

> 참고: 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다(유일하게 하나만 등록해서 공유한다) 따라서 같은 스프링 빈이면 모두 같은 인스턴스다. 설정으로 싱글톤이 아니게 설정할 수 있지만, 특별한 경우를 제외하면 대부분 싱글톤을 사용한다.

## 다른 패키지에서의 스프링 빈 사용 가능할까?

- 불가능하다.
- 패키지는 여러 개를 생성할 수 있지만, 스프링 빈은 맨 첫 패키지에서만 사용 가능하다!

---

# 섹션 5

## 왜 컨트롤러가 정적 파일보다 우선순위가 높을까?

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/bc3bec47-9aaf-4bf6-bfc7-c6aa8f9e6476)


- 위 이미지는 정적 컨텐츠 이미지이다.
- 정적 컨텐츠를 실행할 때도 보면 내장 톰캣 서버에서 우선 스프링 컨테이너를 거치고 나서, 컨테이너에 정적 컨텐츠와 관련된 컨트롤러가 없었기 때문에 정적 파일을 출력을 해주었다.
- 즉, 실행을 하게 된다면 먼저 컨테이너로 이동해서 컨트롤러의 유뮤를 판단하고 그 이후의 것들을 차례대로 실행해주는 셈이다. 즉, 이를 통해 컨트롤러가 정적 파일보다 우선순위가 더 높다는 것을 알 수 있다.
- 이와 같은 원리로 홈 컨트롤러를 통해 이제 컨테이너에 컨트롤러가 생겼기 때문에 컨트롤러가 정적 파일보다 우선순위가 높다는 원리를 이해할 수 있다.

## ❓실무에서 자바 자체 내에서 회원 등록해도 될까?

- 당연히 안된다 ㅎㅎ. 만약, 이게 문제 없다면 데이터베이스가 왜 필요할까?
- 프로그램 자체를 꺼버리면 등록된 모든 사람들이 없어지는 문제가 발생하므로, 자바 자체 내에서 해결하면 안된다!

---

# 섹션 6(1)

- 섹션 6(1)은 프로젝트 중심의 코드 설명 내용이라, 총정리에서는 개념만 다룬다.

## JDBC란? (JDBC = Java Database Connectivity)

- JDBC는 자바에서 데이터베이스 접속할 수 있도록 하는 자바 API이다.

### JDBC가 필요한 이유

![https://blog.kakaocdn.net/dn/5aW2q/btrQTVU3vS9/QXydPIyxuRzghLBCTVYGnK/img.png](https://blog.kakaocdn.net/dn/5aW2q/btrQTVU3vS9/QXydPIyxuRzghLBCTVYGnK/img.png)

- 애플리케이션 서버에서 DB 연결하기 위해서는 [커넥션 연결 / SQL 전달 / 결과 응답]이라는 과정으로 진행된다.
- 만약, 기존에 DB를 MySQL로 사용하다가, Oracle로 바꾸었을 때 아무 문제가 없을까?
    - 아니다. 연결/전달/응답 모두 값이 다르다.
    - 즉, DB를 바꾸면 코드든 쿼리든 바꿔줘야 하는 셈!
    - 이 문제를 해결하고자 JDBC가 필요!!!

### JDBC 표준 인터페이스

![https://blog.kakaocdn.net/dn/b9nfuJ/btrQUBaH0rL/zRsLnkQDXDTidBTDrxskz1/img.png](https://blog.kakaocdn.net/dn/b9nfuJ/btrQUBaH0rL/zRsLnkQDXDTidBTDrxskz1/img.png)

JDBC 표준 인터페이스는 다음 3가지 기능을 표준 인터페이스로 정의해서 제공한다.

- java.sql.Connection : 연결
- java.sql.Statement : SQL을 담은 내용
- java.sql.ResultSet : SQL 요청 응답

### JDBC Driver란?

DB 벤더(회사)에서는 자신의 DB에 맞도록 위 JDBC 인터페이스를 구현해서 라이브러리로 제공하는데, 이것을 JDBC 드라이버라고 한다. 

![https://blog.kakaocdn.net/dn/d73O6h/btrQTFLIopP/yLrBL43RtS02sL3exsbZi1/img.png](https://blog.kakaocdn.net/dn/d73O6h/btrQTFLIopP/yLrBL43RtS02sL3exsbZi1/img.png)

![https://blog.kakaocdn.net/dn/paTbv/btrQUBaIvWf/wVVj4tKQWCmh9Qk8aDLcDk/img.png](https://blog.kakaocdn.net/dn/paTbv/btrQUBaIvWf/wVVj4tKQWCmh9Qk8aDLcDk/img.png)

만약 애플리케이션 서버에서 MySQL DB에 연결하고 싶다면 MySQL 드라이버를 사용하면 된다.

이때 만약 Oracle DB로 변경하고 싶다면 어떻게 하면 될까?

JDBC 드라이버만 Oracle 드라이버로 교체해주면 된다.

이처럼 JDBC를 사용하면 데이터베이스를 변경하더라도 JDBC 구현 라이브러리(JDBC 드라이버)만 변경하면 된다. 또한 개발자는 JDBC 표준 인터페이스 사용법만 학습하더라도 수많은 데이터베이스에 동일하게 적용할 수 있게 된다.

## 스프링 통합 테스트 (Intergration Test)

## 어노테이션

- @SpringBootTest : 스프링 컨테이너와 테스트를 함께 실행한다.
- @Transactional : 테스트 케이스에 이 어노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
- @Test : 해당 메소드가 단위 테스트임을 명시하는 어노테이션

---

# 섹션 6(2)

## 스프링 JdbcTemplate

- 스프링 JdbcTemplate는 실무에서도 많이 쓴다!!!
- 스프링 JdbcTemplate는 순수 JDBC에서 본 반복 코드를 대부분 제거해준다. 하지만 SQL은 직접 작성해야 한다.

### JdbcTemplateMemberRepository 코드

- 스프링 JdbcTemplate 회원 리포지토리

```java
package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
public class JdbcTemplateMemberRepository implements MemberRepository {
    // * #1 시작 *
    private final JdbcTemplate jdbcTemplate;
    public JdbcTemplateMemberRepository(DataSource dataSource) { // DataSource 인젝션
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // * #1 끝 *

    /*
    #2
     */
    @Override
    public Member save(Member member) {
        /*
        #3
         */
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny(); // optioanl 반환
    }
    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny(); // optioanl 반환
    }

		/*
    # 4
     */
    private RowMapper<Member> memberRowMapper() {
        // 람다 스타일
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
```

- #1
    - 스프링 권장 스타일
- #2
    - 순수 JDBC에 있는 save, findById, findAll, findByName에 비해 JdbcTemplateMemberRepository에서 구현된 사용자 정의 메소드들이 훨씬 간단해진 것을 알 수 있다.
- #3
    - SimpleJdbcInsert을 활용해서 간단하게 회원 저장을 수행할 수 있음
- #4
    - 람다 스타일
    - memberRowMapper()를 위의 findById, findAll, findByName 사용자 정의 함수에서도 사용하고 있음

## JPA (Java Persistence API)

- 자바 진영의 ORM 기술 표준
- JPA는 인터페이스의 모음

### ORM

- Object-relational mapping(객체 관계 매핑)
- 객체는 객체대로 설계
- 관계형 데이터베이스는 관계형 데이터베이스대로 설계
- ORM 프레임워크가 중간에서 매핑
- 대중적인 언어에는 대부분 ORM 기술이 존재

### JPA는 애플리케이션과 JDBC 사이에서 동작

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/568644b6-ad75-4757-ba3f-09bb271bca9a)


- 기존에는 JDBC에서 DB로 처리
    - 하지만, SQL 복잡 & 성능 허접
- JPA 등장 이후
    - JPA를 통해 JDBC 가동
    - 덕분에, SQL 작성은 JPA가 알아서
        - 우리는 비즈니스 로직에 더욱 집중 & 성능 증가

## JPA 회원 리포지토리

```java
package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // #1
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
	      // #2
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
        // #3
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
```

- JdbcTemplate에 비해 쿼리문이 매우 적어졌다는 것을 알 수 있다.
- 왜? >>> JPA는 테이블이 아닌, 객체 중심의 개발이니까!!!
- #1
    - JPA는 EntityManager라는 것으로 모든 것을 돈다.
- #2
    - em.persist(member)를 통해 setID도 해주고, insert도 해준다.
        - 얼마나 편안한가!
- #3
    - 여태껏 테이블 대상으로 쿼리를 날렸는데,여기서는 객체를 대상으로 쿼리를 날림

## 스프링 데이터 JPA

- 스프링 부트와 JPA만 사용해도 개발 생산성이 정말 많이 증가하고, 개발해야할 코드도 확연히 줄어든다.
- 여기에 스프링 데이터 JPA를 사용하면, 리포지토리에 구현 클래스 없이 인터페이스만으로 개발을 완료할 수 있다.
    - 그리고 반복 개발해온 기본 CRUD 기능도 스프링 데이터 JPA가 모두 제공한다.
- 이렇기 때문에 개발자는 핵심 비즈니스 로직을 개발하는데 집중할 수 있다.
- 실무에서 관계형 데이터베이스를 사용한다면 스프링 데이터 JPA는 이제 선택이 아니라 필수이다.

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/97379291-1ae3-4bc1-a0c8-cd7f9600f62a)


- 스프링 데이터 JPA 제공 기능
    - 인터페이스를 통한 기본적인 CRUD
    - findByName() , findByEmail() 처럼 메서드 이름 만으로 조회 기능 제공
    - 페이징 기능 자동 제공
- 참고
    - 실무에서는 JPA와 스프링 데이터 JPA를 기본으로 사용하고, 복잡한 동적 쿼리는 Querydsl이라는 라이브러리를 사용하면 된다.
    - Querydsl을 사용하면 쿼리도 자바 코드로 안전하게 작성할 수 있고, 동적 쿼리도 편리하게 작성할 수 있다.
    - 이 조합으로 해결하기 어려운 쿼리는 JPA가 제공하는 네이티브 쿼리를 사용하거나, 앞서 학습한 스프링 JdbcTemplate를 사용하면 된다

---

# 섹션 7

## AOP가 필요한 상황

- 모든 메소드의 호출 시간을 측정하고 싶다면?
- 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern)
- 회원 가입 시간, 회원 조회 시간을 측정하고 싶다면?

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/94bac025-3cd7-464c-9bf7-b1e39fb46abc)


# AOP 적용

- AOP: Aspect Oriented Programming [관점 지향 프로그래밍]
    - 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 분리

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/cda908ed-8fe8-44d1-af48-91b339f3d932)


- AOP가 필요한 상황에서 느꼈듯이, 모든 정의 메소드나 핵심 로직에 시간 측정과 같이 공통 관심 사항이 꼽사리끼게 된다면 정말 많은 시간과 복잡함 등이 몰려올 것이다.
- 하지만, 이미 구현된 것을 통해 원하는 곳에 공통 관심 사항을 적용할 수 있다면?
    - 매우 감사할 것이다! >>> 이게 바로 AOP 인 것이다!

## 스프링의 AOP 동작 방식 설명

- 실제 스프링 빈들 앞에 proxy빈(가짜)을 만들어주고 스프링 컨테이너는 의존성 주입 때 proxy빈을 넣어줍니다.
- 그러면 실제 빈을 실행할 때도 proxy빈이 수행되고 joinpoint가 각각의 메서드들이 실행될 때 개입해 시간체크를 해주는 방식입니다.
- 여기서 joinpoint의 proceed()가 실행될 때 실제 프록시빈이 수행

### AOP 적용 전 의존관계

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/b48b9815-9656-4981-a2ef-bc880320b781)


### AOP 적용 후 의존관계

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/6eccff60-fa93-4a47-9d94-e6059dd4dc35)


### AOP 적용 전 전체 그림

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/74413af6-fa0d-4042-a912-500da74a8d9b)


### AOP 적용 후 전체 그림

![image](https://github.com/irishNoah/Spring-JPA-ORM/assets/80700537/d8336ebf-fb64-49eb-86cf-32dd91e72054)
