# 총정리(ORM&JPA)

태그: ORM & JPA(기본)

- 토글 목차

---

# 섹션01 - JPA 소개

## SQL 중심적인 개발의 문제점

### 문제점

- 객체 지향 언어에서 DB로 전환하는 과정 & DB에서 객체 지향 언어로 전환하는 과정을 할 때마다 반복적인 코드 작성 문제
- 하나의 변수나 필드를 추가할 때마다 발생하는 유지보수의 문제점
- 그리고 Java에 맞추어 객체답게 모델링을 할수록 매핑 작업이 늘어난다.
    - 테이블이 적으면 모를까, 실무에서는 테이블이 최소 수십개인데 객체 모델링을 하면 할수록 Join문 등 발생 가능한 모든 SQL 쿼리를 작성하게 되면 매우 불편할 것

### 해결 방법

- 그래서 객체를 자바 컬렉션에 저장 하듯이 DB에 저장할 수 있는 방법이 과거부터 개발자의 숙제였는데, 이것을 해결을 해 준 것이 **JPA**이다!!!

PA 소개

## JPA

- Java Persistence API
- 자바 진영의 ORM 기술 표준
- JPA는 인터페이스의 모음

## ORM

- Object-relational mapping(객체 관계 매핑)
- 객체는 객체대로 설계
- 관계형 데이터베이스는 관계형 데이터베이스대로 설계
- ORM 프레임워크가 중간에서 매핑
- 대중적인 언어에는 대부분 ORM 기술이 존재

## JPA는 애플리케이션과 JDBC 사이에서 동작

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled.png)

- 기존에는 JDBC에서 DB로 처리
- JPA 등장 이후
    - JPA를 통해 JDBC 가동
    - 덕분에, SQL 작성은 JPA가 알아서
        - 개발자는 비즈니스 로직에 더욱 집중 & 성능 증가

## JPA를 왜 사용해야 하는가?

- SQL 중심적인 개발에서 객체 중심으로 개발
- 생산성 향상 / 코드 간단 / 유지보수 쉬워짐 / 패러다임의 불일치 해결 / 성능 향상 등
- 1차 캐쉬와 동일성 보장 / 쓰기 지연 / 지연 로딩 & 지연 로딩

### JPA와 상속 - 저장 & 조회

![저장](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%201.png)

저장

![조회](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%202.png)

조회

- 이전에는 개발자가 일일이 쿼리를 작성
- JPA 등장 이후
    - 거의 한 줄만 작성
    - 쿼리 자체는 JPA가 알아서 처리

### ✨ 1차 캐시와 동일성 보장

1. 같은 트랜잭션 안에서는 같은 엔티티를 반환 → 약간의 조회 성능 향상
2. DB Isolation Level이 Read Commit이어도 애플리케이션에서 Repeatable Read 보장

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%203.png)

- 쿼리가 동일하더라도, 두 문장을 SQL로써 두 번 실행하는 것이 아니다.
- 첫 번째 쿼리(m1)만 실제 DB에 다녀오고, 동일한 쿼리인(m2)는 캐시로서 값이 제공한다.
- 이를 통해, **결과는 동일해도 네트워크를 다녀오는 횟수를 줄이므로써 성능을 향상**시킨다.

### ✨ 트랜잭션을 지원하는 쓰기 지연 - INSERT

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%204.png)

- 쿼리를 할 때마다 DB에 접근? >>> 네트워크 비용 많아짐
- 만약, 쿼리를 모았다가 한 번에 접근? >>> 네트워크 비용 줄어짐
- 이걸 가능하게 하는 것
    - transcation.begin() & transaction.commit()
- “트랜잭션을 지원하는 쓰기 지연 - UPDATE”도 있지만 이것은 추후 강의에서 설명

### ✨ 지연 로딩과 즉시 로딩

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%205.png)

- 지연 로딩
    - 만약, **두 테이블의 연결 빈도가 적다면 각각을 분리하여 쿼리를 작성**하다가, 필요한 경우에만 Join하면 좋을 것임
    - 이것이 지연 로딩이라고 함
- 즉시 로딩
    - 만약, **두 테이블의 연결 빈도가 높다면 애초에 Join 한 것을 쿼리로 작성**하는 것이 좋을 것임
    - 이것이 즉시 로딩이라고 함

---

# 섹션 02 - JPA 시작하기

## 데이터베이스 방언

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%206.png)

- 각 DB마다 저마다의 문법이 있다. 이것을 데이터베이스 방언이라 한다.
    - 그렇기 때문에 **이 방언을 알아서 해결해 줄 것이 필요하고 이것이 hibernate.dialect**이다.

## **애플리케이션 개발**

- jpaMain.java

```java
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
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
    public static void logic(EntityManager em) {}
}
```

- 코드는 3부분으로 나눠진다.
    - 앤티티 매니저 설정
    - 트랜잭션 관리
    - 비즈니스 로직

## 엔티티 매니저 설정

- 엔티티 매니저란 JPA의 기능 대부분을 제공한다. 엔티티 매니저를 사용해서 엔티티를 데이터베이스에 등록/수정/삭제/조회할 수 있다.
- 엔티티 매니저 생성 과정은 아래와 같다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%207.png)

1. **엔티티 매니저 팩토리 생성**
    
    `EntityManagerFactory emf = Persistence.createEntityManagerFactory("...");`
    
    META-INF/persistence.xml에서 **persistence-unit**을 찾아서 엔티티 매니저 팩토리를 생성한다.
    
    엔티티 매니저 팩토리는 애플리케이션 전체에서 **딱 한 번만 생성**하고 공유해서 사용해야 한다.
    
2. **엔티티 매니저 생성**
    
    `EntityManager em = emf.createEntityManager();`
    
    엔티티 매니저 팩토리에서 엔티티 매니저를 생성한다.
    
    엔티티 매니저는 **데이터베이스 커넥션과 밀접한 관계**가 있으므로 스레드 간에 공유하거나 재사용하면 안된다.
    
3. **종료**
    
    `em.close()`, `emf.close()`
    
    사용이 끝난 엔티티 매니저와 애플리케이션을 종료할 때 엔티티 매니저 팩토리는 반드시 종료해준다. 그래야 데이터베이스 커넥션이 반환된다.
    

## JPQL

> JPQL이란
> 
> 
> Java Persistence Query Language의 약자로
> 
> JPA에서 제공하는 SQL을 추상화한 객체 지향 쿼리 언어이다.
> 

JPQL은 JPA를 사용하면서 **검색 조건이 포함된 SQL**이 필요할 때 사용한다.

### JPQL과 SQL

JPQL은 SQL과 문법이 거의 유사해서 SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN 등을 사용할 수 있다.

**JQPL과 SQL의 차이**는 아래와 같다.

- JPQL은 **엔티티 객체**를 대상으로 쿼리한다. 쉽게 말해 클래스와 필드를 대상으로 쿼리한다.
- SQL은 **데이터베이스 테이블**을 대상으로 쿼리한다.

### 사용 방법

사용방법은 아래와 같다.

1. em.createQuery(JPQL, 반환 타입) 메소드를 실행한다.`TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);`
2. 쿼리 객체를 생성한 후 쿼리 객체의 getResultList() 메소드를 호출하면 된다.`List<Member> members = query.getResultList();`

JPA는 JPQL을 분석해서 적절한 SQL을 만들어 데이터베이스에서 데이터를 조회한다.

---

# 섹션03 - 영속성 관리

## 엔티티 매니저 팩토리와 엔티티 매니저

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%208.png)

- 엔티티 매니저 팩토리는 고객(클라이언트)의 요청이 올 때마다 엔티티 매니저를 생성한다.
    - 참고로, **앤티티 매니저 팩토리는 프로그램에서 한 번만 생성되고, 엔티티 매니저는 여러 번 생성**될 수 있다.
- 엔티티 매니저는 내부적으로 데이터베이스 커넥션을 사용해서 DB를 이용하게 된다.
    - 엔티티 매니저는 엔티티를 저장,수정,삭제,조회하는 등 엔티티와 관련된 일을 처리

## 영속성 컨텍스트(Persistence Context)

- “엔티티를 영구 저장하는 환경”이라는 뜻
- EntityManager.persist(entity);
    - **persist()는 저장한다가 아니라, 영속성을 부여한다**는 뜻!
    - **persist()는 실제로는 DB에 저장하는 것이 아닌, 영속성 컨텍스트라는 곳에 저장한다는 뜻!**

## 엔티티 매니저와 영속성 컨텍스트

- 영속성 컨텍스트는 논리적인 개념
- 엔티티 매니저를 통해서 영속성 컨텍스트에 접근

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%209.png)

## 엔티티의 생명주기

- 비영속(new/transient): 영속성 컨텍스트와 전혀 관계가 없는 상태
    - 객체만 생성되어 있는 상태
- 영속(managed): 영속성 컨텍스트에 저장된 상태
- 준영속(detached): 영속성 컨텍스트에 저장되었다가 분리된 상태
- 삭제(removed): 삭제된 상태

### 비영속

- **엔티티 객체를 생성한 시점**의 상태
- 따라서 영속성 컨텍스트나 데이터베이스와 전혀 관련이 없음

```java
//객체를 생성
Member member = new Member();
member.setId("member1");
member.setUsername("회원1");
```

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2010.png)

### 영속

- 영속성 컨텍스트가 관리하는 엔티티를 영속 상태라고 한다.
    - (영속 상태의 객체는 엔티티 객체를 생성해서 `em.persist(member);`를 수행한 경우 또는 `em.find(Member.class, "member1");` 얻은 경우가 있다.)

```java
//객체를 영속성 컨텍스트에 저장
em.persist(member);
```

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2011.png)

### 준영속

- 영속 상태의 엔티티를 영속성 컨텍스트가 관리하지 않으면 준영속 상태
- 준영속 상태로 만드는 방법은 아래의 메소드를 수행
    - `em.detach(member)`
    - `em.close()`
    - `em.clear()`

### 삭제

- 엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제한 상태

```java
//엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제
em.remove(member);
```

## **영속성 컨텍스트의 특징**

- 영속성 컨텍스트는 트랜잭션을 커밋하는 순간 SQL을 실행한다.
    - 즉, persist() 때에 SQL 실행하는 게 아니라, commit() 때 실행!!!

## **영속성 컨텍스트가 필요한 이유**

### 영속성 컨텍스트의 이점

- 1차 캐시
- 동일성(identity) 보장
- 트랜잭션을 지원하는 쓰기 지연 (transactional write-behind)
- 변경 감지 (Dirty Checking)
- 지연 로딩 (Lazy Loading)

### 1차 캐시

- **영속성 컨텍스트는 영속 상태의 엔티티를 관리하기 위해 내부 캐시를 가지고 있다. 이를 1차 캐시라 한다.**
- 1차 캐시를 쉽게 이야기하면 키는 @Id로 매핑한 필드, 값은 엔티티 인스턴스를 갖는 영속성 컨텍스트에 있는 Map이다.
- 1차 캐시로 얻을 수 있는 이점은 있지만, 실제에서는 큰 이점으로 다가오지 않는다.
    - 고객의 요청에 대해 처리가 완료하여 엔티티 메니저를 삭제할 때 1차 캐시도 삭제하기 때문

### 동일성 보장

- `em.find(Member.class, "member1");`을 호출하면 먼저 1차 캐시에서 엔티티를 찾고 만약 찾는 엔티티가 1차 캐시에 없다면 데이터베이스에서 조회

`em.find()`를 호출하면 일단 1차 캐시에서 식별자 값을 찾는다.

만약 있다면 반환한다.**(이 경우 SQL을 실행하지 않는다.)**

1차 캐시에 없다면,  DB에서 조회 후 1차 캐시에 저장한 영속 상태의 엔티티를 반환한다.

이후 똑같은 식별자 값의 엔티티를 조회할 경우 영속성 컨텍스트에서 반환하는 엔티티이기 때문에 동일성이 보장된다.

여기까지 정리해보면 **영속성 컨텍스트는 내부 캐시를 갖고 있다. 이를 1차 캐시**라고 한다.

`em.find()`를 수행하면 영속성 컨텍스트의 식별자에 해당하는 엔티티를 반환한다.

만약 엔티티가 없다면 DB에서 조회한 결과를 1차 캐시에 저장·반환한다.

엔티티가 있다면 DB 조회 없이 바로 반환한다.

이런 매커니즘으로 동작하여 SQL 실행을 줄여 성능상 이점을 갖고 엔티티 객체의 동일성을 보장한다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2012.png)

- 또한, 동일한 쿼리에 대해서 이미 1차 캐시에 동일한 것이 있다면, 비교할 때 true를 배출
- 1차 캐시로 반복 가능한 읽기(REPEATABLE READ) 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 애플리케이션 차원에서 제공

### **트랜잭션을 지원하는 쓰기 지연**

```java
EntityManager em = emf.createEntityManager();
EntityTransaction transaction = em.getTransaction();
//엔티티 매니저는 데이터 변경 시 트랜잭션을 시작해야 한다.
transaction.begin(); //[트랜잭션] 시작

em.persist(memberA);
em.persist(memberB);
// 여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.  

//커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.  
transaction.commit(); //[트랜잭션] 커밋
```

이 코드를 수행하면 영속성 컨텍스트는 아래의 과정을 거친다.

일단 `em.persist(memberA);`를 수행하면 1차 캐시와 쓰기 지연 SQL 저장소에 MemberA에 해당하는 INSERT SQL을 저장한다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2013.png)

그리고 `em.persist(memberB);`를 수행하면 1차 캐시와 쓰기 지연 SQL 저장소에 MemberB에 해당하는 INSERT SQL을 추가로 저장한다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2014.png)

> **쓰기 지연 SQL 저장소**
> 
> 
> **영속성 컨텍스트는 1차 캐시 공간에 추가로 쓰기 지연 SQL 저장소를 가지고 있다.**
> 
> 쓰기 지연 SQL 저장소는 DB에 등록해야될 엔티티 객체에 대한 INSERT 쿼리를 저장해둔다.
> 

마지막으로 트랜잭션을 커밋(`transaction.commit()`)할 때 쓰기 지연 SQL 저장소에 있는 SQL을 플러시한 뒤에 transaction 커밋을 수행한다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2015.png)

> **플러시**
> 
> 
> 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화하는 작업이다.
> 
> 이때 등록, 수정, 삭제한 엔티티에 대한 **SQL을 데이터베이스**에 보낸다.
> 

위 과정처럼 `em.persist()`를 수행할 때 매번 SQL을 전달하는게 아니라 `transaction.commit()`을 수행할 때 SQL을 전달한다.

이게 바로 트랜젝션을 지원하는 쓰기 지연이다.

### 변경 감지

- JPA를 이용하면 엔티티 값을 변경했을 때 `em.persist()` 또는 `em.update()`를 수행 안해도 DB에 변경이 반영된다. 어떻게 가능할까?

```java
EntityManager em = emf.createEntityManager();
EntityTransaction transaction = em.getTransaction();
transaction.begin(); //[트랜잭션] 시작

//영속 엔티티 조회 
Member memberA = em.find(Member.class, "memberA");

//영속 엔티티 데이터 수정 
memberA.setUsername("hi");
memberA.setAge(10);

transaction.commit(); //[트랜잭션] 커밋
```

- 엔티티 수정 상황 코드를 예로 든다.
- 위 코드를 실행하면 영속성 컨텍스트는 아래 과정을 수행

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2016.png)

> 1차 캐시에 **스냅샷**이 추가되었다.
> 
> 
> **스냅샷이란 엔티티를 영속성 컨텍스트에 보관할 때, 최초 상태를 복사해서 저장해둔 것**이다.
> 
> 스냅샷은 플러시 시점에 스냅샷과 엔티티를 비교해서 변경된 엔티티를 찾을 때 이용한다.
> 
1. 트랜잭션을 커밋하면 엔티티 매니저 내부에서 먼저 플러시(flush())가 호출된다.
    1. 단, 수정 내용에 해당될 경우
2. 엔티티와 스냅샷을 비교해서 변경된 엔티티를 찾는다.
3. 변경된 엔티티가 있으면 수정 쿼리를 생성해서 쓰기 지연 SQL 저장소에 보낸다.
4. 쓰기 지연 저장소의 SQL을 데이터베이스에 보낸다.
5. 데이터베이스 트랜잭션을 커밋한다.

> **주의사항!**
> 
> 
> 변경 감지는 영속성 컨텍스트가 관리하는 영속 상태의 엔티티에만 적용된다.
> 
> 비영속, 준영속처럼 영속성 컨텍스트의 관리 밖의 엔티티는 값을 변경해도 데이터베이스에 반영되지 않는다.
> 

위 과정을 통해 JPA는 엔티티 변경사항을 데이터베이스에 자동으로 반영

🙂 **변경 감지 기본 전략**

**JPA의 변경 감지 기본 전략은 엔티티의 모든 필드를 업데이트한다는 것이다.**

아래의 엔티티 객체와 값을 변경하는 코드를 예로 보자.

```java
@Entity
public class Member {
  @Id
  private Long id;
  private String name;
  private int age;
  private int grade;
  // getter, setter
}
```

```java
Member member = em.find(Member.class, 1L);
member.setAge(10);
```

조회한 member의 나이를 10으로 수정했을 때 예상되는 SQL은 아래와 같다.

```sql
UPDATE MEMBER
SET
  AGE=?
WHERE
  id=?
```

하지만 JPA가 실제 생성하는 SQL은 아래와 같다.

```sql
UPDATE MEMBER
SET
  NAME=?
  AGE=?
  GRADE=?
WHERE
  id=?
```

이렇게 모든 필드를 사용하면 데이터베이스에 보내는 데이터 전송량이 증가하는 단점이 있지만,

다음과 같은 장점으로 인해 모든 필드를 업데이트한다.

- 모든 필드를 사용하면 수정 쿼리가 항상 같다.
    
    따라서 애플리케이션 로딩 시점에 수정 쿼리를 미리 생성해두고 재사용할 수 있다.
    
- 데이터베이스에 동일한 쿼리를 보내면 데이터베이스는 이전에 한 번 파싱된 쿼리를 재사용할 수 있다.

변경 감지를 정리해보면 업데이트를 코드를 따로 작성하지 않아도 된다.

**1차 캐시에서 스냅샷 비교를 통해 update sql을 생성해주기 때문이다.**

**다만 변경 감지의 대상은 영속 상태 엔티티이다.**

J**PA는 기본 전략으로 UPDATE SQL에 모든 필드를 포함한다.**

이유는 수정 쿼리가 항상 같고 데이터베이스가 파싱된 쿼리를 재사용할 수 있다는 장점이 있기 때문이다.

## 플러시

**플러시는 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영**한다.

**중요한 점은 플러시를 수행한다고 해서 기존 캐시가 없어지는 것은 아니다!**

### 플러시 수행 과정

1. 변경 감지가 동작해서 영속성 컨텍스트에 있는 모든 엔티티를 스냅샷과 비교한다.
    
    수정된 엔티티가 있다면 수정 쿼리를 만들어 쓰기 지연 SQL 저장소에 등록한다.
    
2. 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송한다.
    
    (등록, 수정, 삭제 쿼리가 저장된다. *조회 쿼리는 저장되지 않고 바로바로 수행한다.*)
    

### 영속성 컨텍스트 플러시 방법

영속성 컨텍스트를 플러시하는 방법은 아래 3가지이다.

- `em.flush()` 직접 호출한다.
- 트랜잭션 커밋 시 플러시가 자동 호출된다.
- JPQL 쿼리 실행 시 플러시가 자동 호출된다.

## 준영속 상태

> 준영속 상태란 영속성 컨텍스트가 관리하는 영속 상태의 엔티티가 영속성 컨텍스트에서 분리된 것이다.
> 
> 
> **준영속 엔티티는 영속성 컨텍스트가 제공하는 기능을 사용할 수 없다.**
> 

### 준영속 상태로 만드는 방법

영속 엔티티를 준영속 엔티티로 만드는 방법은 아래 3가지이다.

- `em.detach(entity)`: 특정 엔티티만 준영속 상태로 전환한다.
- `em.clear()`: 영속성 컨텍스트를 완전히 초기화한다.
- `em.close()`: 영속성 컨텍스트를 종료한다.

### **엔티티를 준영속 상태로 전환: detach()**

```java
public void testDetached() {
  ...
  //회원 엔티티 생성, 비영속 상태 
  Member member = new Member();
  member.setId("memberA");
  member.setUsername("회원A");
  
  //회원 엔티티 영속 상태 
  em.persist(member);
  
  //회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태 
  em.detach(member);
  
  transaction.commit(); //트랜잭션 커밋 
}
```

위 코드를 수행하면 member에 대한 영속성 컨텍스트의 상태는 아래의 과정을 거친다.

`em.detach(member)`를 호출하면 영속성 컨텍스트에 detach(memberA)를 전달한다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2017.png)

그러면 영속성 컨텍스트는 아래의 과정으로 memberA에 대한 정보를 삭제한다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2018.png)

과정은 아래와 같다.

1. `detach(memberA)` 전달 받았다.
2. 1차 캐시에 memberA에 대한 정보를 제거한다.
3. 쓰기 지연 SQL 저장소에서 memberA에 관련된 SQL을 제거한다.

이렇게 **영속 상태에서 영속석 컨텍스트가 관리하지 않는 상태를 준영속 상태**라고 한다.

### **영속성 컨텍스트 초기화: clear()**

`em.detach()`가 특정 엔티티 하나를 준영속 상태로 만들었다면 `em.clear()`는 영속성 컨텍스트를 초기화해서 해당 영속성 컨텍스트의 모든 엔티티를 준영속 상태로 만든다.

```java
//엔티티 조회, 영속 상태 
Member member = em.find(Member.class, "memberA");

em.clear(); //영속성 컨텍스트 초기화 

//준영속 상태
member.setUsername("changeName");
```

위 코드를 실행하면 영속성 컨텍스트 상태는 아래와 같이 변한다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2019.png)

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2020.png)

### **영속성 컨텍스트 종료: close()**

영속성 컨텍스트를 종료하면 해당 영속성 컨텍스트가 관리하던 영속 상태의 엔티티가 모두 준영속 상태가 된다.

```java
public void closeEntityManage() {
  EntityManagerFactory emf = 
      Persistence.createEntityManagerFactory("jpabook");
      
  EntityManager em = emf.createEntityManager();
  EntityTransaction transaction = em.getTransaction();
  
  transaction.begin(); //[트랜잭션] - 시작
  
  Member memberA = em.find(Member.class, "memberA");
  Member memberB = em.find(Member.class, "memberB");
  
  transaction.commit(); //[트랜잭션] - 커밋 
  
  em.close(); //영속성 컨텍스트 닫기(종료)
}
```

위 코드를 실행하면 영속성 컨텍스트 상태는 아래와 같이 변한다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2021.png)

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2022.png)

### **준영속 상태의 특징**

- 거의 비영속 상태에 가깝다.
    - 영속성 컨텍스트가 관리하지 않기 때문에 1차 캐시, 쓰기 지연, 변경 감지, 지연 로딩을 포함한 **영속성 컨텍스트가 제공하는 어떠한 기능도 동작하지 않는다.**
- 식별자 값을 가지고 있다.
    - 비영속 상태는 식별자 값이 없을 수도 있지만 준영속 상태는 이미 한 번 영속 상태였으므로 반드시 식별자 값을 가지고 있다.
- 지연 로딩을 할 수 없다.
    - 지연 로딩(Lazy Loading)은 실제 객체 대신 프록시 객체를 로딩해두고 해당 객체를 실제 사용할 때 영속성 컨텍스트를 통해 데이터를 불러오는 방법이다.
    - 하지만 준영속 상태는 영속성 컨텍스의 관리 대상이 아니라서 지연 로딩 시 문제가 발생한다.

---

# 섹션 04 - 엔티티 매핑

## @Entity

### 역할

- @Entity가 붙은 클래스는 테이블과 매핑되고 JPA가 관리한다.

### 속성

- @Entity에 사용할 수 있는 속성으로 name이 있다.
    - 다른 패키지에 이름이 같은 엔티티 클래스가 있다면 이름을 지정해서 충돌을 피할수 있다.

### 주의사항

**@Entity 적용 시 주의사항은 아래와 같다.**

- 기본 생성자가 꼭 존재해야 한다. (파라미터가 없는 public 또는 protected 생성자)
- [final, enum, interface, inner] 클래스에 사용할 수 없다.
- 저장할 필드에 final을 사용하면 안 된다.

## @Table

### 역할

엔티티와 매핑할 테이블을 지정한다.

생략하면 매핑한 엔티티 이름을 테이블 이름으로 사용한다.

### 속성

@Table에서 사용할 수 있는 속성은 크게 4가지가 있다.

- name
- catalog
- schema
- uniqueConstraints

**name**

- 매핑할 테이블 이름을 지정한다.
- 생략하면 매핑한 엔티티 이름을 테이블 이름으로 사용한다.

```java
@Entity
@Table
public class Member {
    @Id
    private Long id;
    private String name;
'''
'''
'''
}
```

- 즉, @Table 에 아무것도 안한다면, 매핑 엔티티인 “Member”을 테이블 이름으로 사용하는 것

```java
@Entity
@Table(name="MBR")
public class Member {
    @Id
    private Long id;
    private String name;
'''
'''
'''
}
```

- 위의 경우에는 @Table(name="MBR")을 한 상황. 이 상태에서 프로그램을 실행하면 아래 결과가 나온다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2023.png)

- 보면 테이블명이 Member가 아닌 “MBR”로 나온다. 즉, DB의 “MBR”테이블에서 조회를 한 것!

**uniqueConstraints**

DDL 생성 시 유니크 제약조건을 만들고, 2개 이상의 복합 유니크 제약조건도 만들 수 있다.

이 기능은 스키마 자동 생성 기능을 사용해서 DDL을 만들 때만 사용된다. (이미 만들어진 테이블에 유니크 제약조건을 추가해주진 않는다.)

사용 코드

```java
@Entity
@Table(
    name = "tn_user",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
    })
public user {...}
```

## 데이터베이스 스키마 자동 생성

JPA는 데이터베이스 스키마를 자동으로 생성하는 기능을 지원한다.

스키마 자동 생성 기능을 사용하면 **애플리케이션 실행 시점에 데이터베이스 테이블을 자동으로 생성**한다.

스키마 자동 생성 기능을 적용하려면 아래 코드를 persistence.xml의 property 부분에 추가하자.

`<property name="hibernate.hbm2ddl.auto" value="create"/>`

위 코드의 value 값으로 사용할 수 있는 속성은 아래와 같다.

- create
    - 기존 테이블을 삭제하고 새로 생성한다. **(DROP + CREATE)**
    - 실행 예시
- create-drop
    - create 속성에 추가로 애플리케이션을 종료할 때 생성한 DDL을 제거한다. **(DROP + CREATE + DROP)**
- update
    - 데이터베이스 테이블과 엔티티 매핑정보를 비교해서 변경 사항만 수정한다.
    - (column 삭제는 반영하지 않는다.)
- validate
    - 데이터베이스 테이블과 엔티티 매핑정보를 비교해서 **차이가 있으면 경고를 남기고 애플리케이션을 실행하지 않는다.**
- none
    - 자동 생성 기능을 사용하지 않는다. (자동 생성 기능을 사용하지 않는 또 다른 방법은 `<property name="hibernate.hbm2ddl.auto" value="..."/>` 속성 자체를 삭제하면 된다.
    - 그냥 주석 처리한 거랑 똑같음

> **주의사항!**
> 
> 
> 운영 서버에서 create, create-drop, update 처럼 DDL을 수정하는 옵션은 절대 사용하면 안 된다. (애시당초 DB 계정을 분리시켜야한다.)
> 

## DDL 생성 기능

DDL 생성 기능은 DDL을 자동 생성할 때만 사용된다.

(`hibernate.hbm2ddl.auto`가 create, create-drop, update에서만 동작한다.)

그리고 **JPA의 실행 로직에는 영향을 주지 않는다**.

### #1. nullable, length 설정

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Ff5df6c7d-2bc6-4414-994b-36dc2fda510d%2FDDL%EC%83%9D%EC%84%B101.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Ff5df6c7d-2bc6-4414-994b-36dc2fda510d%2FDDL%EC%83%9D%EC%84%B101.png)

빨간 박스는 DDL에 *not null* 제약조건과 *문자의 크기*를 지정하는 속성을 설정한다.

실제 실행한 결과로 아래의 DDL을 확인할 수 있다.

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F58f72f8b-f8a6-413e-84a2-cc95bd9bab46%2FDDL%EC%83%9D%EC%84%B1%EA%B2%B0%EA%B3%BC01.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F58f72f8b-f8a6-413e-84a2-cc95bd9bab46%2FDDL%EC%83%9D%EC%84%B1%EA%B2%B0%EA%B3%BC01.png)

### #2. 유니크 제약조건 설정

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fa5e997b7-5bb6-467e-92ec-64d9c10a52bc%2FDDL%EC%83%9D%EC%84%B102.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fa5e997b7-5bb6-467e-92ec-64d9c10a52bc%2FDDL%EC%83%9D%EC%84%B102.png)

빨간 박스는 DDL에 *유니크 제약조건*을 설정한다.

생성된 DDL은 아래와 같다.

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F0207f08e-e45c-47d8-826e-1e0c30d942a2%2FDDL%EC%83%9D%EC%84%B102%EA%B2%B0%EA%B3%BC.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F0207f08e-e45c-47d8-826e-1e0c30d942a2%2FDDL%EC%83%9D%EC%84%B102%EA%B2%B0%EA%B3%BC.png)

위에서 말했듯 이런 기능들은 애플리케이션 로직에는 영향을 주지 않는다.

따라서 DDL을 직접 만든다면 굳이 작성할 필요 없는 코드다.

하지만 이 기능을 사용하면 Entity만 보고도 다양한 제약조건을 파악할 수 있는 장점이 있다.

## 필드와 컬럼 매핑

```java
package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {

    @Id
    private Long id;

    @Column(name="name", length = 10) // 엔티티 명은 userName으로, DB 컬럼명을 name으로 매핑하여 사용 지정
    private String userName;

    private Integer age;  // Integer 타입으로 생성하면 DB에서도 가장 Integer랑 가장 적절한 컬럼으로 숫자 타입 생성

    @Enumerated(EnumType.STRING) // 객체에서 ENUM 타입을 쓰고싶을때, DB에는 이넘 타입이 없음(비슷한 것이 있는 DB도 있음)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) // 날짜타입생성 Date, Time, Timestamp 3가지 타입이 있음
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @Lob // DB에 varchar를 넘어서는 문자를 넣고 싶을때, 예를 들면 게시판 contents, 파일 바이너리 등
    private String description;

    // JPA 기본적으로 동적으로 객체를 생성하는 기능이 있어, 기본 생성자도 추가해줘야 된다.
    public Member() {
    }
}
```

- 시간 자료형
    - LocalDate : date
    - LocalTime : time
    - LocalDateTime : timestamp
    - `@Temporal(TemporalType.TIMESTAMP)` : timestamp
        - `@Temporal`은 속성으로 지정한 타입으로 매핑시킨다. (JDK 8 아래에서 사용)
        - TemporalType은 *DATE*, *TIME*, *TIMESTAMP* 이렇게 3가지가 있다.
- @Enumerated
    - `@Enumerated`는 *STRING*, *ORDINAL* 이렇게 2가지가 있다.
        - STRING은 enum 타입의 이름을 사용한다.
        - ORDINAL은 enum 타입에서 순서를 사용한다.
    - 김영한 님은 **ORDINAL이 아닌 STRING을 활용할 것을 추천**했다!
        - 왜냐하면, 숫자로 보면 뭐가 뭔지도 모르기도 하고, 필드(?)의 범위가 늘어나면 뭐가 뭔지 모르자너…
- @Transient
    - 이 어노테이션이 적힌 필드는 db 컬럼에 매핑하지 않는다.
- @Column
    - @Column은 필드와 컬럼에 더 섬세한 설정을 할 때 사용할 수 있는 속성들이 있다.
    - @Column 속성은 2가지로 나눌 수 있다.
        - 애플리케이션 로직에 영향을 주는 속성
            - name, insertable, updatable, table
        - DDL 생성 기능에 영향을 주는 속성
            - nullable, unique, columnDefinition, length, precision, scale

## **@Column 정리**

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2024.png)

> **주의!**
> 
> 
> **자바 열거(enum) 타입을 매핑할 때는 반드시 STRING**을 사용하자.
> 
> STRING은 ORDINAL과 달리 순서가 바껴도 문제되지 않는다.
> 

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2025.png)

## 기본 키 매핑

JPA가 제공하는 데이터베이스 기본 키 생성 전략은 다음과 같다.

- **직접 할당**
    
    기본 키를 애플리케이션에서 직접 할당한다.
    
- **자동 생성**
    
    대리 키 사용 방식으로 3가지 전략이 있다.
    
    - IDENTITY: 기본 키 생성을 데이터베이스에 위임한다.
    - SEQUENCE: 데이터베이스 시퀀스를 사용해서 기본 키를 할당한다.
    - TABLE: 키 생성 테이블을 사용한다.

> 자동 생성 전략이 다양한 이유!
> 
> 
> 데이터베이스 벤더마다 지원하는 방식이 다르기 때문이다.
> 
> *오라클 데이터베이스* : 시퀀스 제공
> 
> *MySQL* : AUTO_INCREMENT 제공
> 

## 기본 키 적용 가능 타입

기본 키 적용 가능한 타입은 아래와 같다.

- 자바 기본형
    - 논리
        - boolean
    - 문자
        - char
    - 숫자
        - 정수 > byte, short, int, long
        - 실수 > flaoat, double
- 자바 래퍼(Wrapper)형
- String
- java.util.Date
- java.sql.Date
- java.math.BigDecimal
- java.math.BigInteger

### 기본 키 적용 방법

`@Id` 어노테이션을 통해서 기본 키 필드를 지정한다.

사용 예

```
@Id
private Long id;
```

### 기본 키 할당 전략

JPA가 지원하는 기본 키 할당 전략은 크게 2가지로 나뉜다.

1. 직접 할당
2. 자동 생성

그리고 자동 생성 전략은 3가지 방식으로 또 나뉜다.

1. IDENTITY
2. SEQUENCE
3. TABLE

각 전략에 대해 자세히 알아보자.

### 직접 할당 전략

기본 키를 직접 할당하기 위해선 `@Id`를 적용하면 된다.

적용 방법은 **기본 키 적용 방법**과 같다.

> **키 값을 지정하지 않으면 벌어지는 일!**
> 
> 
> 키(식별자) 값 없이 저장하면 예외가 발생한다.
> 
> 이때 발생하는 예외는 JPA 구현체의 예외이다. (JPA 표준에는 정의되어 있지 않다.)
> 
> 하이버네이트를 사용할 경우 JPA 최상위 예외인 javax.persistence.PersistenceException이 발생한다.
> 
> 이 예외는 하이버네이트의 org.hibernate.id.IdentifierGenerationException 예외를 포함한다.
> 

### IDENTITY 전략

**IDENTITY 전략은 기본 키 생성을 데이터베이스에 위임하는 전략**이다.

주로 **MySQL**, PostgreSQL, SQL Server, DB2에 사용한다.

사용 예는 아래와 같다.

```java
@Entity
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  ...
}
```

```java
private static void logic(EntityManager em) {
  Board board = new Board();
  em.persist(board);
  System.out.println("board.id = " + board.getId());
}
// 출력 : board.id = 1
```

> 참고
> 
> 
> **IDENTITY 전략과 최적화**
> 
> IDENTITY 전략은 데이터를 데이터베이스 INSERT한 후에 기본 키 값을 조회할 수 있다.
> 
> 따라서 엔티티에 식별자 값을 할당하려면 JPA는 추가로 데이터베이스를 조회해야 한다.
> 
> JDBC3에 추가된 Statement.getGenratedKeys()를 사용하면 데이터를 저장하면서 동시에 생성된 키 값도 얻어 올 수 있다.
> 
> 하이버네이트는 이 메소드를 사용해서 데이터베이스와 한 번만 통신한다.
> 

> 주의
> 
> 
> **IDENETITY 전략은 쓰기 지연 불가**
> 
> 엔티티가 영속 상태가 되려면 식별자가 반드시 필요하다.
> 
> 그런데 IDENTITY 식별자 생성 전략은 엔티티를 데이터베이스에 저장해야 식별자를 구할 수 있으므로 em.persist()를 호출하는 즉시 INSERT SQL이 데이터베이스에 전달된다.
> 
> 따라서 이 전략은 트랜잭션을 지원하는 쓰기 지연이 동작하지 않는다.
> 

### SEQUENCE 전략

**데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트다.**

주로 **오라클**, PostgreSQL, DB2, **H2 데이터베이스**에서 사용할 수 있다.

사용 예는 아래와 같다.

```java
@Entity
@SequenceGenerator (
  name = "BOARD_SEQ_GENERATOR",
  sequenceName = "BOARD_SEQ", //매핑할 데이터베이스 시퀀스 이름
  initialValue = 1, allocationSize = 1)
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
                  generator = "BOARD_SEQ_GENERATOR")
  private Long id;
  ...
}
```

@SequenceGenerator(...)를 통해서 시퀀스 생성기를 등록한다.

그리고 @GeneratedValue()에 키 생성 전략을 `GeneratedType.SEQUENCE`로 설정하고 `generator = SEQUENCE_NAME`을 통해 시퀀스 생성기를 할당한다.

```java
private static void logic(EntityManager em) {
  Board board = new Board();
  em.persist(board);
  System.out.println("board.id = " + board.getId());
}
// 출력 : board.id = 1
```

**@SequenceGenerator 속성 정리**

| 속성 | 기능 | 기본값 |
| --- | --- | --- |
| name | 식별자 생성기 이름 | 필수 |
| sequenceName | 데이터베이스에 등록되어 있는 시퀀스 이름 | hibernate_sequence |
| initialValue | DDL 생성 시에만 사용되고, 시퀀스 DDL을 생성할 때 처음 시작하는 수를 지정 | 1 |
| allocationSize | 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨) | 50 |
| catalog, schema | 데이터베이스 catalog, schema 이름 |  |

> **참고**
> 
> 
> **SEQUNCE 전략과 최적화**
> 
> SEQUENCE 전략은 데이터베이스 시퀀스를 통해 식별자를 조회하는 추가 작업이 필요하다.
> 
> 따라서 **데이터베이스와 2번 통신**한다.
> 
> 1. 식별자를 구하려고 데이터베이스 시퀀스를 조회한다.
> 2. 조회한 시퀀스를 기본 키 값으로 사용해 데이터베이스에 저장한다.
> 
> JPA는 시퀀스에 접근하는 횟수를 줄이기 위해 @SequenceGenerator.allocationSize를 사용한다.
> 
> allocationSize 값만큼 시퀀스를 증가시키고 애플리케이션 메모리에서 식별자를 할당한다.
> 
> 예를들면 allocationSize 값이 10이면 시퀀스를 한 번에 10 증가시킨 다음에 1~10까지는 메모리에서 식별자를 할당한다.
> 

> **IDENTITY 방식과 SEQUENCE 방식의 차이**
> 
> 
> SEQUENCE 전략은 em.persist()를 호출할 때 먼저 데이터베이스 시퀀스를 사용해서 식별자를 조회한다.
> 
> 그리고 조회한 식별자를 엔티티에 할당한 후에 엔티티를 영속성 컨텍스트에 저장한다.
> 
> 이후 트랜잭션을 커밋해서 플러시가 일어나면 엔티티를 데이터베이스에 저장한다.
> 
> IDENTITY 방식은 엔티티를 데이터베이스에 저장한 후 식별자를 조회해서 엔티티의 식별자에 할당한다.
> 

### TABLE 전략

TABLE 전략은 **키 생성 전용 테이블을 하나 만들고 여기에 이름과 값으로 사용할 컬럼을 만들어 데이터베이스 시퀀스와 비슷한 전략**이다.

이 방식은 테이블을 사용하므로 모든 데이터베이스에 적용할 수 있다.

사용 예

```java
@Entity
@TableGenerator(
  name = "BOARD_SEQ_GENERATOR",
  table = "MY_SEQUENCES",
  pkColumnValue = "BOARD_SEQ", allocationSize = 1)
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE,
                  generator = "BOARD_SEQ_GENERATOR")
  private Long id;
  ...
}
```

@TableGenerator를 사용해서 테이블 키 생성기를 등록한다.

그리고 @GeneratedValue에서 키 생성 전략을 TABLE 방식으로 지정하고 생성기를 등록해준다.

```java
private static void logic(EntityManager em) {
  Board board = new Board();
  em.persist(board);
  System.out.println("board.id = " + board.getId());
}
// 출력 : board.id = 1
```

| 속성 | 기능 | 기본값 |
| --- | --- | --- |
| name | 식별자 생성기 이름 | 필수 |
| table | 키생성 테이블명 | hibernate_sequences |
| pkColumnName | 시퀀스 컬럼명 | sequence_name |
| valueColumnName | 시퀀스 값 컬럼명 | next_val |
| initialValue | 초기 값, 마지막으로 생성된 값이 기준이다. | 0 |
| allocationSize | 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨) | 50 |
| catalog, schema | 데이터베이스 catalog, schema 이름 |  |
| uniqueConstraints(DDL) | 유니크 제약 조건을 지정할 수 있다. |  |

> **참고**
> 
> 
> **Table 전략과 최적화**
> 
> TABLE 전략은 값을 조회하면서 SELECT 쿼리를 사용하고 다음 값으로 증가시키기 위해 UPDATE 쿼리를 사용한다. 이 전략은 SEQUENCE 전략과 비교해서 데이터베이스와 한 번 더 통신하는 단점이 있다. (즉, 데이터베이스와 총 3번 통신)
> 
> TABLE 전략을 최적화하려면 SEQUENCE 방식처럼 allocationSize를 지정하면 된다.
> 

## 권장하는 식별자 전략

### 데이터베이스 키 조건

- null 값은 허용하지 않는다.
- 유일해야 한다.
- 변해선 안 된다.

### 키 선택 전략

테이블의 기본 키를 선택하는 전략은 크게 2가지가 있다.

- 자연 키(natural key)
    
    비즈니스에 의미가 있는 키
    
    예 : 주민등록번호, 이메일, 전화번호
    
- 대리 키(surrogate key)
    
    비즈니스와 관련 없는 임의로 만들어진 키, 대체 키로도 불림
    
    예 : 시퀀스, auto_increment, 키 생성 테이블
    

### 그래서 식별자는 뭐로 해?

결론은 **자연 키보다는 대리 키를 권장**한다.

비즈니스 환경은 언젠가 변할 수 있기 때문에 모든 엔티티에 일관된 방식으로 대리 키 사용을 권장한다.

---

# 섹션 05 - 연관관계 매핑 기초

## 객체지향 설계의 목표

- **객체지향 설계의 목표는 자율적인 객체들의 협력 공동체를 만드는 것!!!**

## 단방향 연관관계

여러 연관관계 중에서 다대일(N:1) 단방향 관계부터 알아보자.

### 객체 연관관계와 테이블 연관관계 (다:일)

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F2d5c8de2-f037-4aef-92c0-cdac3b16214c%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EC%97%B0%EA%B4%80%EA%B4%80%EA%B3%8401.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F2d5c8de2-f037-4aef-92c0-cdac3b16214c%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EC%97%B0%EA%B4%80%EA%B4%80%EA%B3%8401.png)

위 그림을 분석해보자.

- **객체 연관관계**
    
    회원 객체는 Member.team 필드로 팀 객체와 연관관계를 맺는다.
    
    회원 객체와 팀 객체는 **단방향 관계**이다. 회원은 Member.team 필드를 통해서 팀을 알 수 있지만 반대로 팀은 회원을 알 수 없다.
    
- **테이블 연관관계**
    
    회원 테이블은 TEAM_ID 외래 키로 팀 테이블과 연관관계를 맺는다.
    
    회원 테이블과 팀 테이블은 **양방향 관계**이다. 회원 테이블의 TEAM_ID 외래 키를 통해서 회원과 팀을 조인할 수 있고 반대로 팀과 회원도 조인할 수 있다.(JOIN 사용)
    

> **외래 키를 이용한 양방향 조인**
> 
> 
> 
> **(회원과 팀을 조인하는 SQL)**
> 
> SELECT *
> 
> FROM MEMBER M
> 
> JOIN TEAM T ON M.TEAM_ID = T.TEAM_ID
> 
> **(팀과 회원을 조인하는 SQL)**
> 
> SELECT *
> 
> FROM TEAM T
> 
> JOIN MEMBER M ON T.TEAM_ID = M.TEAM_ID
> 

그림을 통해서 **테이블은 외래 키를 통해 양방향으로 조회가 가능하지만 객체는 단방향으로만 조회**가 가능한 것을 알 수 있다.

만약 **객체 간에 연관관계를 양방향으로 만들려면 반대쪽에도 필드를 추가**해야 한다. (정확히는 단방향 2개를 만든다.)

## **@ManyToOne, @JoinColumn 속성 정리**

### @ManyToOne

- 다대일(N:1) 관계 매핑 정보이다. 연관관계를 매핑할 때 이렇게 다중성을 나타내는 어노테이션을 명시해야 한다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2026.png)

### @JoinColumn

- 조인 컬럼은 외래키를 매핑할 때 사용한다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2027.png)

## 양방향 연관관계

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fc5af445f-8574-455b-bc49-3b3e8d509e3e%2F%EC%96%91%EB%B0%A9%ED%96%A5%EB%A7%A4%ED%95%9101.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fc5af445f-8574-455b-bc49-3b3e8d509e3e%2F%EC%96%91%EB%B0%A9%ED%96%A5%EB%A7%A4%ED%95%9101.png)

일단 **테이블 연관관계는 단방향 연관관계 때와 달라진건 없다.**

객체 연관관계에서 **Team 객체에 필드로 회원 List가 추가**되었다.

이로써 객체도 양방향 참조가 가능하다.

- 회원 → 팀 (Member.team)
- 팀 → 회원 (Team.members)

> **참고**
> 
> 
> JPA는 List를 포함해서 Collection, Set, Map 같은 다양한 컬렉션을 지원한다.
> 

## em.flush(); 와 em.clear(); 를 하는 특별한 이유

- persist()를 하고 em.find()를 바로 호출하면, 조회 쿼리를 볼 수 없다.
- em.find()는 영속성 컨텍스트에 있는 데이터를 가지고 오기 때문이다.
- 반면에 em.flush(), em.clear()를 하면 DB에 데이터를 반영하고, 영속성 컨텍스트를 지웁니다. 그러면 em.find()를 호출하면 영속성 컨텍스트에 없으니 db에서 조회하고, 조회 쿼리를 볼 수 있습니다.

## 연관관계의 주인

`@OneToMany`에서 mappedBy가 왜 필요할까?

결론부터 말하면 `mappedBy`는 연관관계의 주인이 아니라는 설정이다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2028.png)

위 이미지를 보면, 객체 관점에서 양방향 연관관계는 단방향 2개로 이루어진다. (팀 → 회원, 회원 → 팀)

그리고 테이블에서 양방향 연관관계는 외래 키로 관리된다. (회원 ↔ 팀)

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2029.png)

위 이미지에서 테이블 연관관계를 보면, 여기서 외래 키는 하나인데 객체 참조는 두 포인트가 발생한다. 따라서 둘 사이에 차이가 발생한다. >>> 즉, Member에는 “Team team”이 있고, Team에는 “List member”가 있는데, 이 자체는 테이블 연관관계와 다른점이 발생한다.

❓ **그렇다면 외래 키 관리는 Member와 Team 객체 중 어디서 담당할까?**

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2030.png)

만약, Member 자체에서 team 필드가 바뀐다거나(즉, 멤버 일원의 팀이 변경) 또는 Team에서 member가 바뀐다면 어떻게 해야 할까? 

규칙을 정하지 않는다면, DB나 쿼리가 매우 꼬이게 될 것이다. 그래서 정해진 규칙은 다음과 같다.

기본적으로 외래키를 관리하는 테이블을 매핑한 엔티티가 외래 키를 담당하도록 한다. 이 때 외래 키를 담당하는 엔티티를 **연관관계의 주인**이라 한다. >>> 즉, 여기서는 Member가 주인이 된다!

### 양방향 매핑의 규칙: 연관관계의 주인

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2031.png)

양방향 연관관계 매핑은 반드시 두 연관관계 중 하나를 연관관계 주인으로 정해야 한다.

**연관관계의 주인만이 데이터베이스 연관관계와 매핑되고 외래 키를 관리(등록, 수정, 삭제)할 수 있다. 반면에 주인이 아닌 엔티티는 읽기만 할 수 있다.**

그리고 어떤 연관관계를 주인으로 정할지는 `mappedBy` 속성을 사용하면 된다.

- 주인은 `mappedBy` 속성을 사용하지 않는다.
- 주인이 아니면 `mappedBy` 속성을 사용해서 속성의 값으로 연관관계의 주인을 지정해야 한다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2032.png)

> 연관관계의 주인은 외래 키 관리자가 된다.
> 

> **참고**
> 
> 
> 데이터베이스 테이블의 **다대일, 일대다 관계에서는 항상 다 쪽이 외래키를 갖는다.** 다 쪽인 @ManyToOne은 항상 연관관계의 주인이 되므로 `mappedBy`를 설정할 수 없다. 따라서 @ManyToOne에는 mappedBy 속성이 없다.
> 

## 연관관계 편의 메소드

양방향 연관관계는 결국 양쪽 엔티티에 값을 넣어줘야 한다. 따라서 도메인에서 외래 키를 설정할 때 상대쪽에도 값을 넣을 수 있도록 하나의 기능으로 처리하자. (이를 **연관관계 편의 메소드**라고 한다.)

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fa929a6d0-c331-416e-98e6-57ce728ffb5e%2F%EC%97%B0%EA%B4%80%EA%B4%80%EA%B3%84%ED%8E%B8%EC%9D%98%EB%A9%94%EC%86%8C%EB%93%9C01.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fa929a6d0-c331-416e-98e6-57ce728ffb5e%2F%EC%97%B0%EA%B4%80%EA%B4%80%EA%B3%84%ED%8E%B8%EC%9D%98%EB%A9%94%EC%86%8C%EB%93%9C01.png)

### 연관관계 편의 메소드 작성 시 주의사항

연관관계 편의 메소드를 사용할 때 **주의**할 점은 연관관계 엔티티를 수정할 때 반드시 기존 엔티티에서 제거하고 새로운 엔티티로 설정해야한다.

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Ffef936ec-addd-4b43-a02b-4e037ebc2eb8%2F%EC%97%B0%EA%B4%80%EA%B4%80%EA%B3%84%ED%8E%B8%EC%9D%98%EB%A9%94%EC%86%8C%EB%93%9C%EC%A3%BC%EC%9D%98%EC%82%AC%ED%95%AD01.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Ffef936ec-addd-4b43-a02b-4e037ebc2eb8%2F%EC%97%B0%EA%B4%80%EA%B4%80%EA%B3%84%ED%8E%B8%EC%9D%98%EB%A9%94%EC%86%8C%EB%93%9C%EC%A3%BC%EC%9D%98%EC%82%AC%ED%95%AD01.png)

만약 아래 코드를 수행하지 않으면 다음과 같은 문제가 발생한다.

```java
if (this.team != null) {
  this.team.getMembers().remove(this);
}
```

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F3d6a6870-56ab-460d-b250-de81a75106cb%2F%EC%82%AD%EC%A0%9C%EB%90%98%EC%A7%80%EC%95%8A%EC%9D%80%EA%B4%80%EA%B3%8401.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F3d6a6870-56ab-460d-b250-de81a75106cb%2F%EC%82%AD%EC%A0%9C%EB%90%98%EC%A7%80%EC%95%8A%EC%9D%80%EA%B4%80%EA%B3%8401.png)

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F3958cd98-7db5-4507-90da-a6b5c58b9370%2F%EC%82%AD%EC%A0%9C%EB%90%98%EC%A7%80%EC%95%8A%EC%9D%80%EA%B4%80%EA%B3%8402.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F3958cd98-7db5-4507-90da-a6b5c58b9370%2F%EC%82%AD%EC%A0%9C%EB%90%98%EC%A7%80%EC%95%8A%EC%9D%80%EA%B4%80%EA%B3%8402.png)

member1은 team2과 연관관계를 맺도록 수정했지만 `team1.getMembers()`는 여전히 member1을 참조하고 있다.

---

# 섹션 06 - 다양한 연관관계 매핑

## 연관관계 매핑시 고려사항

엔티티의 연관관계를 매핑할 때는 아래 3가지를 고려해야 한다.

- **다중성 (일대일? 일대다?)**
- **단방향, 양방향**
- if 참조 관계가 양방향일 경우 → **연관관계의 주인**

먼저 두 엔티티의 관계가 일대일인지 일대다인지 다중성을 고려해야 한다. 그리고 두 엔티티 중 한쪽만 참조하는 단방향 관계인지 서로 참조하는 양방향 관계인지 고려해야 한다. 마지막으로 양방향 관계면 연관관계의 주인을 정해야 한다.

### 다중성

연관관계는 4가지 종류가 있다.

- 다대일 (@ManyToOne)
- 일대다 (@OneToMany)
- 일대일 (@OneToOne)
- 다대다 (@ManyToMany) → 사용하면 X

> 실무에서 @ManyToMany을 통한 다대다 매핑은 권장하지 않는다.
> 
> 
> @ManyToMany을 통한 다대다는 고유한 필드를 갖지 못하기 때문이다.
> 

### 참조 관계(단방향, 양방향)

- 테이블
    - 외래 키 하나로 양쪽 조인 가능
    - 사실, 테이블에서는 방향이라는 개념이 없음
- 객체
    - 참조용 필드가 있는 쪽으로만 참조 가능
    - 한쪽만 참조하면 단방향
    - 양쪽이 서로 참조하면 양방향

### 연관관계의 주인

- 테이블은 외래 키 하나로 두 테이블이 연관관계를 맺음
- 객체 양방향 관계는 A->B, B->A 처럼 참조가 2군데
- **객체 양방향 관계**는 참조가 2군데 있음. 둘중 테이블의 외래 키를 관리할 곳을 지정해야함
- 연관관계의 주인: **외래 키를 관리하는 객체**
- 주인의 반대편: 외래 키에 영향을 주지 않음, 단순 조회만 가능

외래 키를 관리하는 대상을 연관관계 주인이라 한다. JPA는 양방향 관계에서 연관관계 주인을 정하는데 `mappedBy` 속성을 사용한다. (`mappedBy`를 사용하지 않으면 연관관계의 주인이고, 사용하면 연관관계의 주인이 아니다.)

> 외래 키를 관리하는 데이터베이스에서 외래 키를 등록, 수정, 삭제할 수 있다는 의미이다.
> 

이제 4가지 연관관계와 단방향, 양방향을 조합해 가능한 모든 연관관계를 알아보자.

- 다대일 : 단방향, 양방향
- 일대다 : 단방향, 양방향 → 일대다는 실무에서 권장하지 않음!!!
- 일대일 : 주 테이블 단방향, 양방향
- 일대일 : 대상 테이블 단방향, 양방향
- 다대다 : 단방향, 양방향 → 다대다는 실무에서 사용하면 안됨!!!

## 다대일 (@ManyToOne)

데이터베이스 테이블의 일대다 관계에서 외래 키는 항상 다쪽에 있다. 

### 다대일 단방향 (N:1)

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F4e845b69-f0eb-4107-a49e-99c292e5ec96%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EB%8B%A8%EB%B0%A9%ED%96%A501.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F4e845b69-f0eb-4107-a49e-99c292e5ec96%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EB%8B%A8%EB%B0%A9%ED%96%A501.png)

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fcd9a120d-ea8c-4539-a65d-793e840c65d6%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EB%8B%A8%EB%B0%A9%ED%96%A5%EC%BD%94%EB%93%9C01.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fcd9a120d-ea8c-4539-a65d-793e840c65d6%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EB%8B%A8%EB%B0%A9%ED%96%A5%EC%BD%94%EB%93%9C01.png)

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F233b946f-07f0-4284-8b71-981c181fcb2e%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EB%8B%A8%EB%B0%A9%ED%96%A5%EC%BD%94%EB%93%9C02.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F233b946f-07f0-4284-8b71-981c181fcb2e%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EB%8B%A8%EB%B0%A9%ED%96%A5%EC%BD%94%EB%93%9C02.png)

Member에서 Team 호출이 가능하다. 하지만 Team에서 Member로 객체 참조가 없어 호출이 불가능하다. 따라서 회원과 팀은 다대일 단방향 연관관계이다.

```java
@ManyToOne
@JoinColumn(name = "team_id")
private Team team;
```

`@JoinColumn(name = "team_id")`를 사용해서 Member.team 필드를 TEAM_ID 외래 키와 매핑했다. 따라서 Member.team 필드로 회원 테이블의 team_id 외래 키를 관리한다.

### 다대일 양방향 (N:1, 1:N)

다대일 양방향의 객체 연관관계에서 **실선이 연관관계의 주인이고 점선은 연관관계의 주인이 아니다.**

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F217338e3-cd84-497c-91aa-cb6ba27f9ef6%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EC%96%91%EB%B0%A9%ED%96%A501.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F217338e3-cd84-497c-91aa-cb6ba27f9ef6%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EC%96%91%EB%B0%A9%ED%96%A501.png)

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F3f76f574-d850-4ee8-9290-a99b0ae75a92%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EC%96%91%EB%B0%A9%ED%96%A5%EC%BD%94%EB%93%9C01.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F3f76f574-d850-4ee8-9290-a99b0ae75a92%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EC%96%91%EB%B0%A9%ED%96%A5%EC%BD%94%EB%93%9C01.png)

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F7c1fe63e-d2f0-4a24-9b9e-1f93d2bd9d63%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EC%96%91%EB%B0%A9%ED%96%A5%EC%BD%94%EB%93%9C02.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F7c1fe63e-d2f0-4a24-9b9e-1f93d2bd9d63%2F%EB%8B%A4%EB%8C%80%EC%9D%BC%EC%96%91%EB%B0%A9%ED%96%A5%EC%BD%94%EB%93%9C02.png)

**양방향은 외래 키가 있는 쪽이 연관관계의 주인**이다. 일대다와 다대일 관계가 있다면 항상 다에 외래 키가 있다. JPA는 외래 키를 관리할 때 연관관계의 주인만 사용한다. **주인이 아닌 Team.member는 조회를 위한 JPQL이나 객체 그래프를 탐색할 때 사용**한다.

양방향 연관관계는 항상 서로를 참조해야 한다. 어느 한 쪽만 참조하면 양방향 연관관계가 성립하지 않는다. 항상 서로를 참조하도록 연관관계 편의 메소드 작성을 권장한다. **(예제에 Member의 joinTeam(), Team의 addMember()가 연관관계 편의 메소드이다.)**

> **주의!**
> 
> 
> **연관관계 편의 메소드는 한 곳에만 작성하거나 양쪽에 작성할 수 있다.**
> 
> **만약 양쪽에 작성한다면 무한루프에 빠지지 않도록 주의해야 한다.**
> 
> 예제는 무한루프에 빠지지 않도록 검사가하는 로직을 작성했다.
> 

## 일대다 (@OneToMany)

일대다 관계는 엔티티를 하나 이상 참조할 수 있으므로 **자바 컬렉션인 Collection, List, Set, Map 중에 하나를 사용**해야 한다.

**참고로 실무에서는 일대다 자체를 안쓰는 게 좋다!**

- **매핑한 객체가 관리하는 외래 키가 다른 테이블에 있는 단점**
    - 본인 테이블에 외래 키가 있으면 엔티티의 저장과 연관관계 처리를 INSERT SQL 한 번으로 끝낼 수 있지만, 다른 테이블에 외래 키가 있다면 연관관계 처리를 위한 UPDATE SQL을 추가로 실행해야 한다.
        - 따라서, 일대다가 아닌, 다대일을 사용해야 함!

## 일대일 (@OneToOne)

- 일대일 관계는 그 반대도 일대일
- 주 테이블이나 대상 테이블 중에 외래 키 선택 가능
- 주 테이블에 외래 키
- 대상 테이블에 외래 키
- **외래 키에 데이터베이스 유니크(UNI) 제약조건 추가**

일대일 관계에서 외래 키는 주 테이블이나 대상 테이블 둘 중 어느 곳이나 외래 키를 가질 수 있다. (다대일은 항상 다쪽이 외래 키를 갖는다.)

- 주 테이블에 외래 키
- 대상 테이블에 외래 키

### 주 테이블에 외래 키

주 객체가 대상 객체를 참조하는 것처럼, 주 테이블에 외래 키를 두고 대상 테이블을 참조한다. 이 방법의 장점은 주 테이블이 외래 키를 가지고 있으므로 주 테이블만 확인해도 대상 테이블과 연관관계가 있는지 알 수 있다.

### 일대일 단방향 (1:1)

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fc5b11951-88c0-43f4-bf35-bd2ecb49dfe0%2F%EC%9D%BC%EB%8C%80%EC%9D%BC%EB%8B%A8%EB%B0%A9%ED%96%A501.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fc5b11951-88c0-43f4-bf35-bd2ecb49dfe0%2F%EC%9D%BC%EB%8C%80%EC%9D%BC%EB%8B%A8%EB%B0%A9%ED%96%A501.png)

- 일대일 단방향 매핑은 다대일(@ManyToOne) 단방향 매핑과 유사

---

### 일대일 양방향 (1:1)

대상 테이블에 반대 방향을 추가하면 된다.

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F5fd7a407-a616-419c-840b-7b64dc3dca13%2F%EC%9D%BC%EB%8C%80%EC%9D%BC%EC%96%91%EB%B0%A9%ED%96%A501.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F5fd7a407-a616-419c-840b-7b64dc3dca13%2F%EC%9D%BC%EB%8C%80%EC%9D%BC%EC%96%91%EB%B0%A9%ED%96%A501.png)

- 다대일 양방향 매핑 처럼 외래 키가 있는 곳이 연관관계의 주인
- 반대편은 mappedBy 적용

```java
package hellojpa;

import javax.persistence.*;

@Entity // JPA가 관리해줄 클래스[JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수]
public class Member {
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String name;

    /* 객체 지향 모델링 >>> 객체의 참조와 테이블의 외래 키를 매핑 */
    @ManyToOne /* 다대일(N:1) 관계 매핑 정보이다. 연관관계를 매핑할 때 이렇게 다중성을 나타내는 어노테이션을 명시해야 한다. */
    @JoinColumn(name = "TEAM_ID") /* 조인 컬럼은 외래 키를 매핑할 때 사용한다. (name 속성은 매핑할 외래 키 이름을 지정한다. */
    private Team team; // 연관관계 주인 ㅇ ==> 주인 아닌 놈은 Team.java에 있다.

    /* 일대일 단방향 */
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    /* 겟터 & 세터 */
.
.
.
```

```java
package hellojpa;

import javax.persistence.*;

@Entity
public class Locker {
    @Id @GeneratedValue
    private Long id;

    private String name;

    /* 일대일 양방향 관계 설정 */
    @OneToOne(mappedBy = "locker")
    private Member member; // 읽기 전용!
}
```

Member 코드는 단방향 때 사용한 코드와 같다. Locker 코드는 Member 참조를 추가해준다. (물론 연관관계 주인이 아니라는 `mappedBy` 속성 추가도 잊지말자.)

### 대상 테이블에 외래 키

- 단방향 관계는 JPA 지원 X
- 양방향 관계는 지원

이 방법의 장점은 테이블 관계를 일대일에서 일대다로 변경할 때, 테이블 구조를 그대로 유지할 수 있다.

JPA에서 일대일 관계 중 대상 테이블에 외래 키가 있는 단방향은 지원하지 않는다.

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fd4656bc4-ca55-487e-a2b1-8ac296a20a21%2F%EC%9D%BC%EB%8C%80%EC%9D%BC%EB%8B%A8%EB%B0%A9%ED%96%A501.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fd4656bc4-ca55-487e-a2b1-8ac296a20a21%2F%EC%9D%BC%EB%8C%80%EC%9D%BC%EB%8B%A8%EB%B0%A9%ED%96%A501.png)

따라서 아래와 같은 일대일 양방향으로 사용해야한다.

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fba4db0ed-10c7-4450-ba5e-6931f2b0c38f%2F%EC%9D%BC%EB%8C%80%EC%9D%BC%EC%96%91%EB%B0%A9%ED%96%A501.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fba4db0ed-10c7-4450-ba5e-6931f2b0c38f%2F%EC%9D%BC%EB%8C%80%EC%9D%BC%EC%96%91%EB%B0%A9%ED%96%A501.png)

- 사실 일대일 주 테이블에 외래 키 양방향과 매핑 방법은 같음

---

### 일대일 정리

- 주 테이블에 외래 키
    - 주 객체가 대상 객체의 참조를 가지는 것처럼, 주 테이블에 외래 키를 두고 대상 테이블을 찾음
    - 객체지향 개발자 선호
    - JPA 매핑 편리
    - 장점: 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능
    - 단점: 값이 없으면 외래 키에 null 허용
- 대상 테이블에 외래 키
    - 대상 테이블에 외래 키가 존재
    - 전통적인 데이터베이스 개발자 선호
    - 장점: 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지
    - 단점: 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩됨(프록시는 뒤에서 설명)

## 다대다 (N:M)

### @JoinTable 속성 정리

- name : 연결 테이블을 지정한다.
- joinColumns : 현재 방향인 회원과 매핑할 조인 컬럼 정보를 지정한다.
- inversionJoinColumns : 반대 방향인 상품과 매핑할 조인 컬럼 정보를 지정한다.

### 다대다 정리

- **다대다는 실무에서 절대로 사용하면 안된다!**

---

# 섹션 07 - 고급[상속관계] 매핑

## 상속 관계 매핑

**관계형 데이터베이스는 객체지향 언어에서 다루는 상속이라는 개념이 없다. 대신 슈퍼타입 서브타입 관계 (Super-Type Sub-Type Relationship)라는 모델링 기법이 객체의 상속 개념과 가장 유사하다.** 

ORM에서 이야기하는 상속 관계 매핑은 객체의 상속 구조와 데이터베이스의 슈퍼타입 서브타입 관계를 매핑하는 것이다.

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F4a8ff9c5-2251-44e2-bbde-2e2068589079%2F%EC%83%81%EC%86%8D%EA%B4%80%EA%B3%8401.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F4a8ff9c5-2251-44e2-bbde-2e2068589079%2F%EC%83%81%EC%86%8D%EA%B4%80%EA%B3%8401.png)

슈퍼타입 서브타입 논리 모델을 실제 물리 모델인 테이블로 구현할 때는 3가지 방법을 선택할 수 있다.

- 각각의 테이블로 변환
    - 모두 테이블로 만들고 조회할 때 조인을 사용한다. (JPA에서는 조인 전략이라 한다.)
- 통합 테이블로 변환
    - 모든 칼럼을 하나의 테이블로 구성한다. (JPA에서는 단일 테이블 전략이라 한다.)
- 서브타입 테이블로 변환
    - 서브 타입마다 하나의 테이블을 만든다. (JPA에서는 구현 클래스마다 테이블 전략이라 한다.)

## 상속 관계 매핑 - 1. 조인 전략

조인 전략은 **엔티티 각각을 모두 테이블로 만들고 자식 테이블이 부모 테이블의 기본 키를 받아서 기본 키 + 외래 키로 사용하는 전략**이다.

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fb79d6b12-7b38-48c3-ba69-9e6a89d84dd7%2F%EC%A1%B0%EC%9D%B8%EC%A0%84%EB%9E%B501.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fb79d6b12-7b38-48c3-ba69-9e6a89d84dd7%2F%EC%A1%B0%EC%9D%B8%EC%A0%84%EB%9E%B501.png)

### 특징

- 조회할 때 **조인**을 자주 사용한다.
    - 부모 테이블의 기본 키를 받아서 기본 키 + 외래 키를 사용하는 전략이기 때문에 조회할 때 조인을 자주 사용한다.
- 타입을 구분하는 컬럼을 추가한다.
    - 객체는 타입으로 구분할 수 있지만 테이블은 타입의 개념이 없기 때문에 타입 구분을 위한 칼럼을 추가한다.
- 실무
    - **데이터도 많고, 비즈니스적으로도 중요하고, 발전 가능성도 높을 경우 이 전략 채택**

### 장점

- 테이블이 **정규화**된다.
- **외래 키 참조 무결성 제약조건**을 활용할 수 있다.
- **저장 공간을 효율적**으로 사용한다.

### 단점

- 조회할 때 **조인이 많이 사용되므로 성능이 저하**될 수 있다.
- **조회 쿼리가 복잡**하다.
- 데이터를 등록할 **INSERT SQL을 두 번 실행**한다.

### 예제

`Item.java`

```java
package hellojpa;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 상속 관계 매핑 > 조인 전략
@DiscriminatorColumn // 저장된 자식 테이블을 DTYPE에 따라 구분
public abstract class Item { // 고급 매핑 // Item 클래스는 추상화 해줘야 함 >>> abstract 없으면 독단적으로도 사용할 수도 있단 말
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

    /* getter & setter */
		.
		.
		.
}
```

`Album.java`

```java
package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") // DTYPE 축약
public class Album extends Item { // 고급매핑

    private String artist;

}
```

`Movie.java`

```java
package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@DiscriminatorValue("M") // DTYPE 축약
public class Movie extends Item { // 고급 매핑

    private String director;
    private String actor;

    /* getter & setter */
		.
		.
		.
}
```

`Book.java`

```java
package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // DTYPE 축약
public class Book extends Item { // 고급 매핑

    private String author;
    private String isbn;

}
```

`jpaMain.java`

```java
package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class jpaMain {
    public static void main(String[] args){
        final EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();
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

        Movie movie = new Movie();
        movie.setName("바람과 함께 사라지다");
        movie.setPrice(10000);
        movie.setActor("bbbb");
        movie.setDirector("aaaa");

        em.persist(movie);

        em.flush();
        em.clear();

    }
}
```

### 결과

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2033.png)

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2034.png)

- insert를 2번 하게 된다. Item, Movie에게 각각 한번씩 한다.
- @DiscriminatorColumn 을 통해서 DType이 현재 어떤 자식 클래스의 데이터를 담고있는 지 알려준다.

## 주요 어노테이션

- **@Inheritance(strategy = InherianceType.JOINED)**
    - 상속 매핑은 부모 클래스에 @Inheritance를 사용해야 한다. 그리고 매핑 전략을 지정한다. 여기서는 조인 전략을 사용하므로 strategy 속성으로 InheritanceType.JOINED를 설정했다.
- **@DiscriminatorColumn(name = "DTYPE")**
    - 부모 클래스에 구분 컬럼을 지정한다. 이 컬럼으로 저장된 자식 테이블을 구분할 수 있다. (기본값은 DTYPE이다.)
- **@DiscriminatorValue("M")**
    - 엔티티를 저장할 때 구분 컬럼에 입력할 값을 지정한다. 만약 Movie 엔티티를 저장하면 구분 컬럼인 DTYPE에 `M`이 저장된다.
- **@PrimaryKeyJoinColumn(name = "book_id")**
    - 자식 테이블의 기본 키 컬럼명을 지정한다. Book 엔티티에 기본 키 + 외래 키의 컬럼명은 `book_id`로 된다.

## 상속 관계 매핑 - 2. 단일 테이블 전략

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F8a05c78c-c992-4103-a616-3429ab7a7785%2F%EB%8B%A8%EC%9D%BC%ED%85%8C%EC%9D%B4%EB%B8%94%EC%A0%84%EB%9E%B501.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F8a05c78c-c992-4103-a616-3429ab7a7785%2F%EB%8B%A8%EC%9D%BC%ED%85%8C%EC%9D%B4%EB%B8%94%EC%A0%84%EB%9E%B501.png)

### 특징

- 구분 컬럼을 통해 저장된 타입을 구분한다.구분 컬럼(`@DiscriminatorColumn`)을 반드시 사용한다.
- 실무
    - **데이터 수도 적고, 발전 가능성도 없을 때 이 전략을 많이 채택**

### 장점

- 조인이 필요 없으므로 일반적으로 **조회 성능이 빠르다.**
- **조회 쿼리가 단순**하다.

### 단점

- **자식 엔티티가 매핑한 컬럼은 모두 null을 허용해야 한다.**
- 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다. (상황에 따라 조회 성능이 느려질 수 있다.)
    - 하지만, 이게 발생할 확률은 적다.

### 예제

`Item.java`

```java
package hellojpa;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 관계 매핑 > 단일 테이블 전략
@DiscriminatorColumn // 저장된 자식 테이블을 DTYPE에 따라 구분 & 단일 테이블 전략에서는 사용 안해도 됨(알아서, DTYPE 생성)
public class abstract Item { // 고급 매핑 // Item 클래스는 추상화 해줘야 함 >>> abstract 없으면 독단적으로도 사용할 수도 있단 말
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

    /* 이하 getter & setter */
}
```

- **@Inheritance(strategy = InherianceType.SINGLE_TABLE)**
    - 단일 테이블 전략을 사용하므로 strategy 속성으로 InheritanceType.SINGLE_TABLE을 설정했다.
- **@DiscriminatorColumn 생략 가능**
    - 단일 테이블 전략에서는 사용 안해도 됨(알아서 DTYPE 생성)
        - 알아서 DTYPE 생성하는 이유
            - 내가 Ablum인지 Movie인지 뭔지 구분을 할 수가 없기 때문

### 실행 결과

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fdf34a48f-05ea-434d-971d-03efa1fd8493%2F%EB%8B%A8%EC%9D%BC%ED%85%8C%EC%9D%B4%EB%B8%94%EC%8B%A4%ED%96%89%EA%B2%B0%EA%B3%BC01.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2Fdf34a48f-05ea-434d-971d-03efa1fd8493%2F%EB%8B%A8%EC%9D%BC%ED%85%8C%EC%9D%B4%EB%B8%94%EC%8B%A4%ED%96%89%EA%B2%B0%EA%B3%BC01.png)

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2035.png)

- Item 테이블이 만들어지고 컬럼으로 Item, Album, Book, Movie 엔티티에 작성한 속성 값이 모두 들어간다.

## 상속 관계 매핑 - 3. 구현 클래스마다 테이블 전략(쓰면 X)

구현 클래스마다 테이블 전략은 자식 엔티티마다 테이블을 만든다. (이때 슈퍼타입을 위한 테이블은 생성되지 않는다.) 그리고 자식 테이블 각각에 필요한 컬럼이 모두 있다.

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F9bfce72e-9ba0-437d-88c9-f36e3ecba7a3%2F%EA%B5%AC%ED%98%84%ED%81%B4%EB%9E%98%EC%8A%A4%EB%A7%88%EB%8B%A4%ED%85%8C%EC%9D%B4%EB%B8%94%EC%A0%84%EB%9E%B501.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F9bfce72e-9ba0-437d-88c9-f36e3ecba7a3%2F%EA%B5%AC%ED%98%84%ED%81%B4%EB%9E%98%EC%8A%A4%EB%A7%88%EB%8B%A4%ED%85%8C%EC%9D%B4%EB%B8%94%EC%A0%84%EB%9E%B501.png)

### 특징

- 구분 컬럼을 사용하지 않는다.
- @DiscriminatorColumn 이 동작하지 않는다. -> **테이블이 이미 다 다르니 의미가 없다.**
- Item 테이블을 없애 버리고 거기있는 속성들을 다 밑으로 내리는 것이다.
    - 하지만 Item으로 조회를 할 때에는 union으로 모든 테이블을 가져오게 된다. 각 테이블(movie, book ,album) 명으로 조회할 때는 문제 없긴 하다.
- **실무에서는 절대 사용하면 안된다!!!**

### 장점

- 서브 타입을 구분해서 처리할 때 효과적이다.
- not null 제약조건을 사용할 수 있다.

### 단점

- 여러 자식 테이블을 함께 조회할 때 성능이 느리다(SQL에 UNION을 사용해야 한다.)
- 자식 테이블을 통합해서 쿼리하기 어렵다.

### 예제

이번 예제도 `Item` 엔티티만 살펴본다.

```java
package hellojpa;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 상속 관계 > 구현 클래스마다 테이블 전략
@DiscriminatorColumn // 저장된 자식 테이블을 DTYPE에 따라 구분 & 단일 테이블과 구현 클래스 전략에서는 사용 안해도 됨(알아서, DTYPE 생성)
public abstract class Item { // 고급 매핑 // Item 클래스는 추상화 해줘야 함 >>> abstract 없으면 독단적으로도 사용할 수도 있단 말
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

    /* 이하 getter & setter 부분 */
}
```

- @Inheritance(strategy = InherianceType.TABLE_PER_CLASS)구현 클래스마다 테이블 전략을 사용하므로 strategy 속성으로 InheritanceType.TABLE_PER_CLASS 설정했다.
- @DiscriminatorColumn 는 필요 없다.

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F41d0b19a-a770-46a2-99d1-bb51d3bd642c%2F%EA%B5%AC%ED%98%84%ED%81%B4%EB%9E%98%EC%8A%A4%EB%A7%88%EB%8B%A4%ED%85%8C%EC%9D%B4%EB%B8%94%EC%A0%84%EB%9E%B5%EC%8B%A4%ED%96%89%EA%B2%B0%EA%B3%BC01.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F41d0b19a-a770-46a2-99d1-bb51d3bd642c%2F%EA%B5%AC%ED%98%84%ED%81%B4%EB%9E%98%EC%8A%A4%EB%A7%88%EB%8B%A4%ED%85%8C%EC%9D%B4%EB%B8%94%EC%A0%84%EB%9E%B5%EC%8B%A4%ED%96%89%EA%B2%B0%EA%B3%BC01.png)

Item 엔티티에 매핑되는 테이블은 생성되지 않는다. 하지만 Item 엔티티의 속성들은 자식 엔티티의 속성에 추가되어 테이블 칼럼으로 추가된다.

## @MappedSuperclass

`**@MappedSuperclass`는 부모 클래스를 테이블과 매핑하지 않고 부모 클래스를 상속받는 자식 클래스에게 매핑 정보만 제공한다. (실제 테이블과 매핑되지 않는다.)**

![https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F2112f46b-cc1e-4dce-98e8-90dde841d0b4%2FMappedSuperclass01.png](https://velog.velcdn.com/images%2Fhansoleee%2Fpost%2F2112f46b-cc1e-4dce-98e8-90dde841d0b4%2FMappedSuperclass01.png)

### @MappedSuperclass 특징

- 상속관계 매핑 X
- 엔티티 X, 테이블과 매핑 X
- 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공
- 조회, 검색 불가(em.find(BaseEntity) 불가)
- 직접 생성해서 사용할 일이 없으므로 **추상 클래스 권장**
- 테이블과 관계 없고, 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모으는 역할
- 주로 등록일, 수정일, 등록자, 수정자 같은 전체 엔티티에서 공통으로 적용하는 정보를 모을 때 사용
- 참고
    - @Entity 클래스는 엔티티나 @MappedSuperclass로 지정한 클래스만 상속 가능

### 코드

`BaseEntity.java`

```java
package hellojpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 매핑 정보만 받는 슈퍼(부모) 클래스
public abstract class BaseEntity { // 동일한 속성에 대해서 상속을 해준다. & 직접 생성해서 사용할 일이 없으므로 추상 클래스 권장

    @Column(name = "INSERT_MEMBER")
    private String createdBy;
    private LocalDateTime createDate;
    @Column(name = "UPDATE_MEMBER")
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

    /* 이하, getter & setter*/
}
```

- **@MappedSuperclass**
    - 매핑 정보만 받는 슈퍼(부모) 클래스임을 의미
- **BaseEntity 클래스**
    - 동일한 속성에 대해서 상속을 해준다.
    - BaseEntity에 해당하는 테이블은 생성되지 않고 상속받은 객체들에게 매핑 정보만 제공한다.
- **abstract 키워드**
    - 직접 생성해서 사용할 일이 없으므로 추상 클래스 권장

`Member.java`

```java
package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // JPA가 관리해줄 클래스[JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수]
public class Member extends BaseEntity { // 속성에 대해서 BaseEntity에서 상속
.
.
.
.
}
```

`Team.java`

```java
package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends BaseEntity { // 속성에 대해서 BaseEntity에서 상속
.
.
.
.
}
```

`jpaMain.java`

```java
package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

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

        Member member = new Member();
        member.setUserName("usert");
        member.setCreatedBy("kim");
        member.setCreateDate(LocalDateTime.now());

        em.persist(member);

        em.flush();
        em.clear();

    }
}
```

`결과`

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2036.png)

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2037.png)

- BaseEntity에 들어가 있는 속성을 상속받아 Member 테이블이 생성된 것을 알 수 있다.
    - INSERT_MEMBER나 UPDATE_MEMBER 처럼 @Column(name = “지정속성명") 을 한 것이 잘 반영됐음을 알 수 있다.
- extend를 해준 Member 를 살펴보면 도메인에는 적혀있지 않던 createdBy ~ lastModifiedDate 까지 생겼다.
    - 한마디로 같이 쓰고 싶은 속성을 **중복 없이 쓸 수 있게 해준다.**
        - 만약, 이렇게 안했다면 각 클래스 파일마다 동일한 속성을 만들어서 사용했어야겠지?
- **BaseEntity는 테이블과 관계 없고, 단순히 엔티티가 공통으로 사용하는 매핑정보를 모으는 역할**
    - 주로 등록일, 수정일 , 등록자, 수정자, 같은 전체 엔티티에서 공통으로 적용하는 정보를 모을 때 사용 >>> 실무에서도 많이 사용됨!

---

# 섹션 8

## 프록시

### 프록시 기초

- em.find() vs em.getReference()
- em.find() : **데이터베이스를 통해서 실제 엔티티 객체 조회**
    - 즉, 진짜로 객체를 준다.
- em.getReference() : **데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회**
    - 진짜 객체가 아닌, 하이버네이트 내부에 있는 라이브러리를 사용해 가짜인 프록시 객체를 준다.

![https://blog.kakaocdn.net/dn/pvGtR/btrtVn7X745/ZDp99wcVE2zhz49GtnQQz1/img.png](https://blog.kakaocdn.net/dn/pvGtR/btrtVn7X745/ZDp99wcVE2zhz49GtnQQz1/img.png)

### em.find()

```java
//비즈니스 로직
    public static void logic(EntityManager em) {
        /* 로직 작성 */

        Member member = new Member();
        member.setUserName("pcy");

        em.persist(member);

        /* 아래 두 줄을 통해 영속성 컨텍스트 깔끔하게 만들어주기 */
        em.flush();
        em.clear();

        // em.find() 활용한 조회
        Member findMember = em.find(Member.class, member.getId());
        System.out.println("findMember.id = " + findMember.getId());
        System.out.println("findMember.username = " + findMember.getUserName());

    }
```

### em.getRefernece() - 프록시 조회

```java
public static void logic(EntityManager em) {
        /* 로직 작성 */

        Member member = new Member();
        member.setUserName("pcy");

        em.persist(member);

        /* 아래 두 줄을 통해 영속성 컨텍스트 깔끔하게 만들어주기 */
        em.flush();
        em.clear();

        /* getReference (프록시) */
        // select query 나가지 않음! 프록시 객체 반환됨. Id는 이미 알고 있는 값
        Member findMember = em.getReference(Member.class, member.getId());
        System.out.println("findMember.id = " + findMember.getId());
        // 이 시점에 select query 나감! DB에만 있는 값을 조회하는 시점에 조회
        System.out.println("findMember.username = " + findMember.getUserName());

    }
```

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2038.png)

- findMember에서 “HibernateProxy”가 나온 것을 볼 수 있다.

## 프록시 초기화

[https://oopy.lazyrockets.com/api/v2/notion/image?src=https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F515b9790-43af-4db8-8183-f41743988af6%2F_2020-06-17__8.08.24.png&blockId=483c549f-960f-49fc-940e-213800498424](https://oopy.lazyrockets.com/api/v2/notion/image?src=https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F515b9790-43af-4db8-8183-f41743988af6%2F_2020-06-17__8.08.24.png&blockId=483c549f-960f-49fc-940e-213800498424)

```java
Member member = em.getRefernce(Member.class, "id1");//(1)
member.getName(); //(2)
```

- 코드 라인에서 **getReference()를 호출하면 프록시 객체를 가져온 다음, getName()을 호출하면 JPA가 영속성 컨텍스트에 초기화 요청**을 한다.
- 영속성 컨텍스트에서는 실제 db를 조회해서 가져온 이후, 실제 Entity에 값을 넣어 생성한 다음, 프록시 객체는 실제 엔티티를 연결해서 실제 엔티티를 반환한다.
- 그 이후에는 이미 초기화 되어 있는 프록시 객체이기에 해당 엔티티를 반환한다.

## 프록시의 특징

[https://oopy.lazyrockets.com/api/v2/notion/image?src=https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fc735bfb1-0081-47c3-a841-afe5d863cc2b%2F_2020-06-17__8.05.35.png&blockId=5af6c4ea-2afa-4711-8479-b6b1dd21eddc](https://oopy.lazyrockets.com/api/v2/notion/image?src=https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fc735bfb1-0081-47c3-a841-afe5d863cc2b%2F_2020-06-17__8.05.35.png&blockId=5af6c4ea-2afa-4711-8479-b6b1dd21eddc)

- 실제 클래스를 상속받아서 만들어짐
- 실제 클래스와 겉 모양이 같다.
- 사용하는 입장에서는 진짜 객체인지 구분 필요가 없다(이론적으로)
- 프록시 객체는 실제 객체의 참조(target)를 보관한다.
- 프록시 객체를 호출(getName())하면 프록시 객체는 실제 객체의 메소드 호출

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2039.png)

1) 프록시는 처음 사용할 때 한 번만 초기화

2) 프록시 객체를 초기화 할 때 프록시 객체가 실제 엔티티로 바뀌는 것은 아님, 초기화되면 프록시 객체를 통해 실제 엔티티에 접근 가능

```java
public static void logic(EntityManager em) {
    /* 로직 작성 */

    Member member1 = new Member();
    member1.setUserName("member1");
    em.persist(member1);

    Member member2 = new Member();
    member2.setUserName("member2");
    em.persist(member2);

    Member member3 = new Member();
    member3.setUserName("member3");
    em.persist(member3);

    /* 아래 두 줄을 통해 영속성 컨텍스트 깔끔하게 만들어주기 */
    em.flush();
    em.clear();

    Member m1 = em.find(Member.class, member1.getId());
    Member m2 = em.find(Member.class, member2.getId());
    Member m3 = em.getReference(Member.class, member3.getId());
    
    // 타입 비교 >>> true 출력
    System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass())); // true

    // 타입 비교 >>> false 출력
    System.out.println("m1 == ref : " + (m1.getClass() == m3.getClass())); // false

}
```

```java
m1.getClass() == m3.getClass() //false
m1 instanceof Member // true
m3 instanceof Member // true
```

- 위 코드와 같이, 프록시 객체는 원본 엔티티를 상속받음, 따라서 타입 체크시 주의해야함( ==비교 실패, 대신 **instance of** 사용)

```java
Member m1 = em.find(Member.class, member1.getId());
System.out.println("m1 = " + m1.getClass());//Member

Member reference = em.getReference(Member.class, member1.getId());
System.out.println("reference = " + reference.getClass()); //Member

System.out.println("m1 == reference : " + (m1 == reference)); // true
```

- 단, 위 코드와 같이 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
    - 이미 Member를 1차 캐시에도 올라와 있는데, 프록시를 반환할 필요가 없다.
    - 변수 m1이나 reference나 동일한 member1에 대해서 조회를 수행
        - m1을 통해서 1차 캐시에 올라와져 있기 때문에, 변수 reference도 getClass()의 결과가 Member인 것!

```java
public static void logic(EntityManager em) {
        /* 로직 작성 */

        Member member1 = new Member();
        member1.setUserName("member1");
        em.persist(member1);

        /* 아래 두 줄을 통해 영속성 컨텍스트 깔끔하게 만들어주기 */
        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, member1.getId());
        Member findMember = em.find(Member.class, member1.getId());

        System.out.println("refMember = " + refMember.getClass()); // proxy
        System.out.println("findMember = " + findMember.getClass()); // proxy
        // 타입 비교
        System.out.println("refMember == findMember : " + (refMember == findMember)); // true

    }
```

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2040.png)

- 반대로 위 코드와 같이 getReference()로 프록시 객체를 가지고 있으면 실제로 find()를 했을 때도 프록시 객체를 반환.

```java
Member refMember = em.getReference(Member.class, member.getId());
System.out.println("refMember = "+ refMember.getClass());//Proxy

em.detach(refMember);
//em.clear

refMember.getUsername(); //org.hibernate.LazyInitializationException
```

- 위 코드와 같이, 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제 발생

## 프록시 확인

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2041.png)

### 프록시 인스턴스의 초기화 여부 확인

```java
package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

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
            logic(em,emf);      //비즈니스 로직 실행
            tx.commit();    //[트랜잭션] - 커밋
        } catch (Exception e) {
            tx.rollback();  //[트랜잭션] - 롤백
        } finally {
            em.close();     //[엔티티 매니저] - 종료
        }
        emf.close();        //[엔티티 매니저 팩토리] - 종료
    }

    //비즈니스 로직
    public static void logic(EntityManager em, EntityManagerFactory emf) {
        /* 로직 작성 */

        Member member1 = new Member();
        member1.setUserName("member1");
        em.persist(member1);

        /* 아래 두 줄을 통해 영속성 컨텍스트 깔끔하게 만들어주기 */
        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, member1.getId());
        System.out.println("refMember = "+ refMember.getClass()); //Proxy

        refMember.getUserName(); // 초기화를 해줘야 초기화가 true, 안해주면 false
        // 초기화 여부 확인 : 위 코드 라인에서 초기화를 해줬기 때문에 결과는 true
        System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));

    }
}
```

- PersistenceUnitUtil.isLoaded(Object entity) → entityManagerFactory.getPersistenceUnitUtil().isLoaded(object)
- logic(em,emf) 로 변경된 거 인지하기!

### 프록시 클래스 확인 방법

- entity.getClass().getname() 출력(..javasist.. or HibernateProxy...)

### 프록시 강제 초기화

```java
public static void logic(EntityManager em) {
        /* 로직 작성 */

        Member member1 = new Member();
        member1.setUserName("member1");
        em.persist(member1);

        /* 아래 두 줄을 통해 영속성 컨텍스트 깔끔하게 만들어주기 */
        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, member1.getId());
        System.out.println("refMember = "+ refMember.getClass()); //Proxy

        // refMember.getUserName(); // 이것도 초기화 방법이긴 하지만 좀 그럼...

        /* 강제 초기화 */
        Hibernate.initialize(refMember); // 강제 초기회

    }
```

- org.hibernate.Hibernate.initialize(entity);

### 참고: JPA 표준은 강제 초기화 없음

강제 호출: method.getNameI();

## 지연 로딩

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2042.png)

→ Member 정보만 사용하는 비즈니스 로직이라면 굳이 같이 조회할 필요 없음

- 이런 경우
    - **지연로딩 LAZY를 사용해서 프록시로 조회**
    - 실제 Team을 사용하는 시점에 초기화 (DB 조회)

## 지연 로딩 원리

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2043.png)

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2044.png)

- 즉, Member와 Team이 함께 사용될 일이 거의 없다면, 따로 구분했다가 Team이 필요한 경우에만 초기화를 통해 DB 조회 실행하면 된다.
- 근데, 만약 Member와 Team이 함께 사용될 일이 많다면 어떻게 해야 할까?
    - 즉시 로딩 원리를 사용하면 된다!

## 지연 로딩 코드

### Member.java

```java
package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // JPA가 관리해줄 클래스[JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수]
public class Member extends BaseEntity { // 속성에 대해서 BaseEntity에서 상속
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String name;

    /* 객체 지향 모델링 >>> 객체의 참조와 테이블의 외래 키를 매핑 */
    @ManyToOne(fetch = FetchType.LAZY) /* 다대일 매핑, FetchType.LAZY >>> 지연로딩*/
    @JoinColumn(name = "TEAM_ID") /* 조인 컬럼은 외래 키를 매핑할 때 사용한다. (name 속성은 매핑할 외래 키 이름을 지정한다. */
    private Team team; // 연관관계 주인 ㅇ ==> 주인 아닌 놈은 Team.java에 있다.

    /* getter & setter */
    
}
```

- fetch = FetchType.LAZY 를 통해 지연 로딩 가능

### jpaMain.java

```java
public static void logic(EntityManager em) {
        /* 로직 작성 */

        Team team = new Team();
        team.setName("teamA");
        em.persist(team);

        Member member1 = new Member();
        member1.setUserName("member1");
        em.persist(member1);

        member1.setTeam(team);

        /* 아래 두 줄을 통해 영속성 컨텍스트 깔끔하게 만들어주기 */
        em.flush();
        em.clear();

        Member m = em.find(Member.class, member1.getId());
        System.out.println("m = " + m.getTeam().getClass()); // proxy

        System.out.println("============");
        m.getTeam().getName(); // getName()을 할 때 초기화
        System.out.println("============");

    }
```

### 실행 결과

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2045.png)

- proxy를 통해서 생성된 이후, 초기화를 할 때 SQL 쿼리가 나간다는 것을 알 수 있다.

## 즉시 로딩

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2046.png)

- 둘이 한 번에 자주 사용된다면 “즉시” 로딩 기법을 사용하도록 하자!

### 원리

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2047.png)

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2048.png)

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2049.png)

### 즉시 로딩 코드

`Member.java`

```java
package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // JPA가 관리해줄 클래스[JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수]
public class Member extends BaseEntity { // 속성에 대해서 BaseEntity에서 상속
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String name;

    /* 객체 지향 모델링 >>> 객체의 참조와 테이블의 외래 키를 매핑 */
    @ManyToOne(fetch = FetchType.EAGER) /* 다대일 매핑, FetchType.EAGER >>> 즉시 로딩*/
    @JoinColumn(name = "TEAM_ID") /* 조인 컬럼은 외래 키를 매핑할 때 사용한다. (name 속성은 매핑할 외래 키 이름을 지정한다. */
    private Team team; // 연관관계 주인 ㅇ ==> 주인 아닌 놈은 Team.java에 있다.

    /* getter & setter */

}
```

`jpaMain.java`

```java
public static void logic(EntityManager em) {
        /* 로직 작성 */

        Team team = new Team();
        team.setName("teamA");
        em.persist(team);

        Member member1 = new Member();
        member1.setUserName("member1");
        em.persist(member1);

        member1.setTeam(team);

        /* 아래 두 줄을 통해 영속성 컨텍스트 깔끔하게 만들어주기 */
        em.flush();
        em.clear();

        Member m = em.find(Member.class, member1.getId());
        System.out.println("m = " + m.getTeam().getClass()); // 객체

        System.out.println("============");
        // 초기화가 필요 없음
        System.out.println("teamName = " + m.getTeam().getName()); 
        System.out.println("============");

   
```

`실행 결과`

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2050.png)

- 지연 로딩과 달리, 즉시 로딩은 처음부터 join을 통해서 가져오기 때문에 proxy가 필요 없다.
- `m = class [hellojpa.Team](http://hellojpa.Team)` 를 통해서 proxy가 아닌 진짜 객체가 들어가 있는 것을 알 수 있다.
- 하지만, **실무에서는 즉시 로딩 방법을 기피**해야 한다!

### 프록시와 즉시로딩 주의점

- **가급적, 지연 로딩만 사용(특히 실무에서)**
- 즉시로딩을 적용하면 예상하지 못한 SQL이 발생
    - **즉시로딩은 JPQL에서 N+1 문제를 일으킴**
- **@ManyToOne, @OneToOne은 기본이 즉시 로딩 → 따라서, LAZY로 설정해줘야 한다!!!**
- @OneToMany, @ManyToMany는 기본이 지연로딩
- 한 번에 조회해야 할 때는 fetch join 사용!

### 지연 로딩 활용 - 이론 & 실무

`지연 로딩 활용 - 이론`

- Member와 Team은 자주 함께 사용 → 즉시 로딩
- Member와 Order는 가끔 사용 → 지연 로딩
- Order와 Product는 자주 함께 사용 → 즉시 로딩

`지연 로딩 활용 -실무`

- **모든 연관관계에 지연로딩을 사용해라!**
- 실무에서 즉시로딩을 사용하지 마라!
- **JPQL fetch 조인이나, 엔티티 그래프 기능을 사용해라!**
- 즉시 로딩은 예상치 못한 쿼리가 나간다!

## 영속성 전이(CASCADE)

### 영속성 전이: CASCADE

- 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 만들고 싶을 때 사용
    - 예: 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장
- 객체 저장시, 객체의 연관관계도 함께 저장하고 싶을 때 사용
- **하나의 부모만이 해당 자식 객체를 관리할 때는 사용 가능. 하지만 그 자식 객체를 여러군데에서 관리하고, 연관관계가 맺어있을 때는 문제가 발생. 즉, 소유자가 하나일 때만 사용하기**→ (1) 단일 엔티티에 종속적일 때. (2) 라이프사이클이 거의 유사할 때 사용

![https://blog.kakaocdn.net/dn/wo7VR/btrtTLVy7zV/tIalnumoA5y0yXZopdj8KK/img.png](https://blog.kakaocdn.net/dn/wo7VR/btrtTLVy7zV/tIalnumoA5y0yXZopdj8KK/img.png)

![https://blog.kakaocdn.net/dn/bxP4l8/btrtRW37yGo/NjRJTjPzvL78IShyz8aiyk/img.png](https://blog.kakaocdn.net/dn/bxP4l8/btrtRW37yGo/NjRJTjPzvL78IShyz8aiyk/img.png)

```
@OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
```

### **주의점**

- 영속성 전이는 연관관계를 매핑하는 것과 **아무 관련이 없음**
- 엔티티를 영속화할 때, 연관된 엔티티도 함께 영속화하는 편리함을 제공할 뿐

### **CASCADE의 종류**

- **ALL : 모두 적용**
- **PERSIST : 영속**
- **REMOVE : 삭제**
- MERGE : 병합
- REPRESH : REFRESH
- DETACH : DETACH

### 코드

`Parent.java`

```java
package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    /* cascade = CascadeType.ALL 를 활용해서 영속성 전이 해주기 */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> childList = new ArrayList<>();

    public void addChild(Child child){
        childList.add(child);
        child.setParent(this);
    }

    /* 이하, getter & setter */
}
```

`Child.java`

```java
package hellojpa;

import javax.persistence.*;

@Entity
public class Child {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private  Parent parent;

    /* 이하, getter & setter */
}
```

`jpaMain.java`

```java
public static void logic(EntityManager em) {
        /* 로직 작성 */

        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();
        parent.addChild(child1);
        parent.addChild(child2);

        /* 영속성 전이 상태에 있으면, child1과 childe2를 persist 안했음에도 불구하고
        * parent만 했는데 child1과 childe2가 persist가 된 것을 알 수 있다. */
        em.persist(parent);
        //em.persist(child1);
        //em.persist(child2);

    }
```

`실행 결과`

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2051.png)

- jpaMain에서 persist는 1번밖에 안했는데, 실행 결과에서는 insert가 3개 된 것을 알 수 있다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2052.png)

- DB에도 결과가 잘 들어간 것을 확인할 수 있다.

## 고아 객체

- **고아 객체 제거 : 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제**
- **orphanRemoval = true**

```java
Parent parent = em.find(Parent.class, id);
parent.getChildren().remove(0);//자식 엔티티를 컬렉션에서 제거 -> 바로 해당 오펀 객체 DB에서 DELETE됨
```

### 코드

`Parent.java`

```java
package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    /* orphanRemoval = true를 활용해서 고아 객체 제거하기 */
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Child> childList = new ArrayList<>();

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
	
	  /* getter & setter */ 
}
```

`jpaMain.java`

```java
public static void logic(EntityManager em) {
        /* 로직 작성 */

        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();
        parent.addChild(child1);
        parent.addChild(child2);

        em.persist(parent);
        em.persist(child1);
        em.persist(child2);

        em.flush();
        em.clear();

        Parent findParent = em.find(Parent.class, parent.getId());
        /* findParent가 제거될 때, 자식 객체들도 제거*/
        em.remove(findParent);

    }
```

`실행 결과`

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2053.png)

- Parent와 더불어서 child들도 같이 delete된 것을 알 수 있다.

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2054.png)

- DB에도 Parent나 child 모두 사라진 것을 알 수 있다.

### **고아 객체 - 주의점**

- 참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아 객체로 보고 삭제하는 기능
- **참조하는 곳이 하나일 때 사용해야 함!**
- **특정 엔티티가 개인 소유할 때 사용**
- @OneToOne, @OneToMany만 가능
- 참고 : 개념적으로 부모를 제거하면 자식은 고아가 된다. 따라서 고아 객체 제거 기능을 활성화하면, 부모를 제거할 때 자식도 함께 제거된다. 이것은 CascadeType.REMOVE처럼 동작한다.

### 영속성 전이 + 고아 객체, 생명주기

- **CascadeType.ALL + orphanRemoval=True**
- 스스로 생명주기를 관리하는 엔티티는 em.persist()로 영속화, em.remove()로 제거
- **두 옵션을 모두 활성화하면 부모 엔티티를 통해서 자식의 생명주기를 관리할 수 있음**
- 도메인 주도 설계(TDD)의 Aggregate Root개념을 구현할 때 유용
    - → Aggregate Root : DB는 Aggregate Root를 통해서 접근하는 개념

# 섹션 09 - 값 타입

## 타입 분류

### 엔티티 타입

- @Entity로 정의하는 객체
- **데이터가 변해도 식별자(pk)로 지속해서 추적 가능**
- 예) 회원 엔티티의 키나 나이 값을 변경해도 식별자로 인식 가능

### 값 타입

- int, Integer, String처럼 단순히 값으로 사용하는 자바 기본 타입이나 객체
- **식별자가 없고 값만 있으므로 변경시 추적 불가**
- 예) 숫자 100을 200으로 변경하면 완전히 다른 값으로 대체

`값 타입 분류`

- **기본값 타입**
    - 자바 기본 타입(int, double)
    - 래퍼 클래스(Integer, Long)
    - String
- **임베디드 타입(embedded type, 복합 값 타입)**
- **컬렉션 값 타입(collection value type)**

## 기본값 타입

- 예): String name, int age
- **생명주기를 엔티티에 의존**
    - 예) 회원을 삭제하면 이름, 나이 필드도 함께 삭제
- **값 타입은 공유하면 X**
    - 예) 회원 이름 변경시 다른 회원의 이름도 함께 변경되면 안됨

### 참고: 자바의 기본 타입은 절대 공유X

- int, double 같은 기본 타입(primitive type)은 절대 공유X
- 기본 타입은 항상 값을 복사함
- Integer같은 래퍼 클래스나 String 같은 특수한 클래스는 공유 가능한 객체이지만 변경 X

## 임베디드 타입

- **새로운 값 타입을 직접 정의할 수 있음**
- JPA는 임베디드 타입(embedded type)이라 함
- 주로 기본 값 타입을 모아서 만들어서 **복합 값 타입**이라고도 함
- int, String과 같은 값 타입

### 사용법

- **@Embeddable : 값 타입을 정의하는 곳에 표시**
- **@Embedded : 값 타입을 사용하는 곳에 표시**
- **기본 생성자 필수**

### 장점

- 재사용
- 높은 응집도
- Period.isWork() 처럼 해당 값 타입만 사용하는 의미 있는 메소드를 만들 수 있음
- 임베디드 타입을 포함한 모든 값 타입은, 값 타입을 소유한 엔티티에 생명주기를 의존함

### 테이블 매핑

- 임베디드 타입은 엔티티의 값일 뿐이다
- 임베디드 타입을 사용하기 전과 후에 **매핑하는 테이블은 같다**
- 임베디드 타입의 값이 null이면 매핑한 컬럼 값은 모두 null
- 객체와 테이블을 아주 세밀하게(fine-grained) 매핑하는 것이 가능
- 잘 설계한 ORM 애플리케이션은 매핑한 테이블의 수보다 클래스의 수가 더 많음

### **@AttributeOverride > 속성 재정의**

- 한 엔티티에서 임베디드 타입에 있는 것과 같은 값 타입을 사용하면?
    - 컬럼명이 중복됨
- @AttributeOverrides, @AttributeOverride를 사용해서 컬럼명 속성을 재정의

`예시 설계`

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2055.png)

`상세한 설계`

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2056.png)

`추상화한 설계`

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2057.png)

- 추상화한 설계 부분에 대해 실제 Java에서는 위와 같이 설계한다.

### 예시 코드

`Period.java`

```java
package hellojpa;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable // 값 타입을 정의하는 곳에 표시
public class Period {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Period() { // 기본생성자 필수
    }

    /* 이하, getter & setter */
    
}
```

`Address.java`

```java
package hellojpa;

import javax.persistence.Embeddable;

@Embeddable // 값 타입을 정의하는 곳에 표시
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address() { // 기본생성자 필수
    }

    // constructor
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    /* 이하, getter & setter */
}
```

`Member.java`

```java
package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // JPA가 관리해줄 클래스[JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수]
public class Member { // 속성에 대해서 BaseEntity에서 상속 >>> 임베디드 타입 때는 사용 x(지저분해서)
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String name;

    /* 객체 지향 모델링 >>> 객체의 참조와 테이블의 외래 키를 매핑 */
    @ManyToOne(fetch = FetchType.EAGER) /* 다대일 매핑, FetchType.EAGER >>> 즉시 로딩*/
    @JoinColumn(name = "TEAM_ID") /* 조인 컬럼은 외래 키를 매핑할 때 사용한다. (name 속성은 매핑할 외래 키 이름을 지정한다. */
    private Team team; // 연관관계 주인 ㅇ ==> 주인 아닌 놈은 Team.java에 있다.

    /* 기간 : Period (임베디드 타입) */
    @Embedded // 값 타입을 정의하는 곳에 표시
    private Period workPeriod;

    /* 주소 : Address (임베디트 타입) */
    @Embedded // 값 타입을 정의하는 곳에 표시
    private Address homeAddress;

    /* 이하, getter & setter */
}
```

`jpaMain.java`

```java
//비즈니스 로직
    public static void logic(EntityManager em) {
        /* 로직 작성 */

        Member member = new Member();
        member.setUsername("hello1");
        member.setHomeAddress(new Address("seoul", "garosu", "100"));
        member.setWorkPeriod(new Period());

        em.persist(member);
    }
```

`실행 결과`

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2058.png)

- 원하는 대로 DB는 생성되고, java 자체는 java 답게 프로그래밍된 것을 알 수 있다.

## 값 타입과 불변 객체

### 값 타입의 개념

- 값 타입은 복잡한 객체 세상을 조금이라도 단순화하려고 만든 개념이다. 따라서 값 타입은 단순하고 안전하게 다룰 수 있어야 한다.

### 값 타입 공유 참조

- 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험함
- 부작용(side effect) 발생

**값타입들은 기본적으로 공유를 하면 안됩니다.** 예를 들어 다른 회원의 나이가 변경되었다고 다른 회원의 이름도 변경되면 안된다는 말입니다.

**기본값 타입**의 **기본 타입**은 애초에 값을 복사하기 때문에 공유를 할 수 없습니다. 하지만 **래퍼클래스나 String**은 참조값을 복사하기 때문에 공유가 가능하지만 수정이 불가능하기때문에 괜찮습니다.

### 값 타입 복사

- 값 타입의 실제 인스턴스인 값을 공유하는 것은 위험
- 대신 값(인스턴스)를 복사해서 사용

**임베디드 타입**은 직접 정의한 객체타입이기 때문에 기본값 타입과는 다르게 **공유가 가능하고 수정 또한 가능해서 유의**해야 합니다. 

### 객체 타입의 한계

- 항상 값을 복사해서 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있다.
- 문제는 임베디드 타입처럼 직접 정의한 값 타입은 자바의 기본 타입이 아니라 객체 타입이다.
- 자바 기본 타입에 값을 대입하면 값을 복사한다.
- 객체 타입은 참조 값을 직접 대입하는 것을 막을 방법이 없다.
- 객체의 공유 참조는 피할 수 없다.

아래와 같이 회원1과 회원2의 주소가 정말 우연히 같아서 하나의 Address객체를 만든 뒤 같이 넣어주면 공유하게 됩니다!! 회원1이나 2가 이후 주소를 변경해도 함께 변경되기 때문에 유의해야 합니다.

`(예시 코드)`

```java
public static void logic(EntityManager em) {
        /* 로직 작성 */

        Address address = new Address("old", "seogu", "12345");

        Member m1 = new Member();
        m1.setUsername("m1");
        m1.setHomeAddress(address);
        em.persist(m1);

        Member m2 = new Member();
        m2.setUsername("m2");
        m2.setHomeAddress(address);
        em.persist(m2);

        /* 값 변경*/
        m1.getHomeAddress().setCity("new");

    }
```

- 둘 다 Address("old", "seogu", "12345")로 세팅되어 있는 상황
- m1의 도시 값만 old → new로 바꾸고 싶었는데, 과연 m1 값만 바뀌게 될까? 아니다.

(실행 결과)

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2059.png)

원치 않던 m2의 도시 값도 함께 바뀐 것을 알 수 있다.

![https://velog.velcdn.com/images%2Frmswjdtn%2Fpost%2F244df0bf-984e-4c26-8a84-971997f81590%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202022-02-01%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%205.31.46.png](https://velog.velcdn.com/images%2Frmswjdtn%2Fpost%2F244df0bf-984e-4c26-8a84-971997f81590%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202022-02-01%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%205.31.46.png)

### 불변 객체

- **객체 타입을 수정할 수 없게 만들면, 부작용을 원천 차단!**
- 값 타입은 불변 객체(immutable object)로 설계해야 함
- 불변 객체: 생성 시점 이후 절대 값을 변경할 수 없는 객체
- 생성자로만 값을 설정하고 수정자(Setter)를 만들지 않으면 됨
- 참고: Integer, String은 자바가 제공하는 대표적인 불변 객체

**side effect**는 오류를 찾기도 매우 까다롭기 때문에, 이런 문제를 막기 위해서는 같은 값이라도 임베디드 타입의 각각 **기본값 타입들을 복사해서 넘겨주는 방식**을 취해야 합니다.

![https://velog.velcdn.com/images%2Frmswjdtn%2Fpost%2F952c2b29-a973-4926-a3b8-a8f016d1616b%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202022-02-01%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%205.41.09.png](https://velog.velcdn.com/images%2Frmswjdtn%2Fpost%2F952c2b29-a973-4926-a3b8-a8f016d1616b%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202022-02-01%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%205.41.09.png)

이를 코드화한 방식은 아래와 같다.

```java
Address copyAddress = member1.getAddress();
member1.setAddress( new Address(
	copyAddress.getCity(), copyAddress.getStreet(), copyAddress.getZipCode()
	))
```

이 방식을 기반으로 한 예제 코드 및 실행 결과는 아래와 같다.

`(예시 코드)`

```java
public static void logic(EntityManager em) {
        /* 로직 작성 */

        Address address = new Address("old", "seogu", "12345");

        Member m1 = new Member();
        m1.setUsername("m1");
        m1.setHomeAddress(address);
        em.persist(m1);

        Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

        Member m2 = new Member();
        m2.setUsername("m2");
        m2.setHomeAddress(copyAddress);
        em.persist(m2);

        /* 값 변경*/
        m1.getHomeAddress().setCity("new");

    }
```

(실행 결과)

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2060.png)

또 다른 해결방법은 **불변객체로 만드는 것**입니다. 이는 생성시점 이후에 변경할 수 없는 객체로 String등이 이에 속합니다. 불변객체로 만드는 방법은 **setter를 정의하지 않거나 private로 정의하면 됩니다.**

```java
    public String getCity() {
        return city;
    }

    private void setCity(String city) { // setter를 private로 정의
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    private void setStreet(String street) { // setter를 private로 정의
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    private void setZipcode(String zipcode) { // setter를 private로 정의
        this.zipcode = zipcode;
    }
```

이런 방법들을 통해 **불변이라는 작은 제약으로 부작용이라는 큰 재앙을 막을 수 있다.**

## 값 타입의 비교

- **동일성(identity) 비교**: 인스턴스의 참조 값을 비교, == 사용
- **동등성(equivalence) 비교**: 인스턴스의 값을 비교, equals() 사용
- 값 타입은 a.equals(b)를 사용해서 동등성 비교를 해야 함
- 값 타입의 equals() 메소드를 적절하게 재정의(주로 모든 필드 사용)

### 1. 동일성 비교

- 인스턴스의 참조 값을 비교, == 사용

**==** 을 이용해서 비교하는 방법이며 인스턴스의 **참조 값을 비교**합니다. 자바의 **기본타입**은 **값이 같으면 같은 공간**을 쓰기 때문에 아래와 같은 경우 최종적으로 b와 c는 같은 값, 같은 주소를 갖게 됩니다.

```java
int a = 10;
int b = a; // < 기본타입 >공유 되는 것 아님 : 값만 넘어감
int c = 10;

a = 20;

System.out.println("a = " + a);
System.out.println("b = " + b);
System.out.println("c = " + c);
System.out.println("b == c : " + (b == c));
System.out.println("b = " + System.identityHashCode(b));
System.out.println("c = " + System.identityHashCode(c));
```

`출력`

```
a = 20
b = 10
c = 10
b == c : true
b = 157627094
c = 157627094
```

### 2. 동등성 비교

- 인스턴스의 값을 비교, equals() 사용

동등성 비교는 **인스턴스의 값을 비교**하는 것이며 **equals()**사용해서 판별합니다. 자바의 기본타입을 제외하고는 동등성 비교를 해줘야 값만 비교가 가능합니다.

따라서 값 타입은 **자바의 기본타입을 제외하고는 모두 equals연산을 사용**해야하며 오브젝트 타입 같이 따로 정의해준 타입은 equals를 따로 정의해줘야합니다.

**Address같은 경우 이렇게 equals를 override해서 각각의 요소마다 비교**를 할 수 있도록 해야합니다.**(단축키 ctrl + insert -> equals and hashcode)**

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Address address = (Address) o;
    return Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(zipcode, address.zipcode);
}

@Override
public int hashCode() {
    return Objects.hash(city, street, zipcode);
}
```

`Address.java`

```java
package hellojpa;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable // 값 타입을 정의하는 곳에 표시
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address() { // 기본생성자 필수
    }

    // constructor
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    /* getter & setter */
  
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(zipcode, address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipcode);
    }
}
```

`jpaMain.java`

```java
//비즈니스 로직
    public static void logic(EntityManager em) {
        /* 로직 작성 */

        Address address1 = new Address("old", "street", "123");
        Address address2 = new Address("old", "street", "123");

				// 동일성 비교
        System.out.println("address1 == address2 " + (address1 == address2));
        // 동등성 비교
				System.out.println("address1 equals address2 " + (address1.equals(address2)));
    }
```

`실행 결과`

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2061.png)

- 동일성 비교는 false가, 동등성 비교는 true가 나온 것을 알 수 있다.

## 값 타입 컬렉션

- 값 타입을 하나 이상 저장할 때 사용
- @ElementCollection, @CollectionTable 사용
- 데이터베이스는 컬렉션을 같은 테이블에 저장할 수 없다.
- 컬렉션을 저장하기 위한 별도의 테이블이 필요함

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2062.png)

이거를 만들고자 함

## 코드

### Member.java

```java
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

    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns =
    @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressHistory = new ArrayList<>();

    /* 객체 지향 모델링 >>> 객체의 참조와 테이블의 외래 키를 매핑 */
    @ManyToOne(fetch = FetchType.EAGER) /* 다대일 매핑, FetchType.EAGER >>> 즉시 로딩 */
    @JoinColumn(name = "TEAM_ID") /* 조인 컬럼은 외래 키를 매핑할 때 사용한다. (name 속성은 매핑할 외래 키 이름을 지정한다. */
    private Team team; // 연관관계 주인 ㅇ ==> 주인 아닌 놈은 Team.java에 있다.

    /* getter & setter */

   .
.
.

}
```

- favoriteFoods처럼 값으로 사용되는 칼럼이 하나면 @Column을 사용해서 컬럼명을 지정할 수 있다.

### 실행 결과

![https://blog.kakaocdn.net/dn/7Xero/btqBlz8EUYk/k6vb8nbnscEBnDzOqbCACK/img.png](https://blog.kakaocdn.net/dn/7Xero/btqBlz8EUYk/k6vb8nbnscEBnDzOqbCACK/img.png)

![https://blog.kakaocdn.net/dn/bWDjJQ/btqBphMupvs/6U6u1vx1xUIkWP2UKlgdRK/img.png](https://blog.kakaocdn.net/dn/bWDjJQ/btqBphMupvs/6U6u1vx1xUIkWP2UKlgdRK/img.png)

![https://blog.kakaocdn.net/dn/5xCUR/btqBqoRs78A/2lKTuKO1b0BHP8JemmnH4k/img.png](https://blog.kakaocdn.net/dn/5xCUR/btqBqoRs78A/2lKTuKO1b0BHP8JemmnH4k/img.png)

- 확인할 수 있다시피 FAVORITE_FOOD와 ADDRESS 테이블이 생성이 되었다.

## 값 타입 저장 예제

### jpaMain.java

```java
//비즈니스 로직
    public static void logic(EntityManager em) {
        /* 로직 작성 */

        Member member = new Member();
        member.setUsername("member1");
				//임베디드 값 타임
        member.setHomeAddress(new Address("homeCity", "street1", "12345"));
				
				//기본값 타입 컬렉션
        member.getFavoriteFoods().add("치킨");
        member.getFavoriteFoods().add("족발");
        member.getFavoriteFoods().add("피자");

				//임베디드 값 타입 컬렉션
				// 값 타입 저장
        member.getAddressHistory().add(new Address("old1", "street1", "12345"));
        member.getAddressHistory().add(new Address("old2", "street1", "12345"));

        em.persist(member);
    }
```

마지막에 member 엔티티만 영속화했다.

> JPA는 이때 member 엔티티의 값 타입도 함께 저장한다.

실제 실행되는 INSERT SQL은

- member : INSERT SQL 1번
- member.homeAddress : 컬렉션이 아닌 임베디드 값 타입이므로 회원테이블을 저장하는 SQL에 포함된다.
- member.favoriteFoods : INSERT SQL 3번
- member.addressHistory : INSERT SQL 2번

```java
// 예시

INSERT INTO MEMBER(ID, CITY, STREET, ZIPCODE)VALUES (1, '통영', '몽돌해수욕장', '660-123')
INSERT INTOFAVORITE_FOODS (MEMBER_ID, FOOD_NAME)VALUES (1, "짬뽕")
INSERT INTOFAVORITE_FOODS (MEMBER_ID, FOOD_NAME)VALUES (1, "짜장")
INSERT INTOFAVORITE_FOODS (MEMBER_ID, FOOD_NAME)VALUES (1, "탕수육")
INSERT INTOADDRESS(MEMBER_ID, CITY, STREET, ZIPCODE)VALUES (1, '서울', '강남', '123-123')
INSERT INTOADDRESS(MEMBER_ID, CITY, STREET, ZIPCODE)VALUES (1, '서울', '강북', '000-000')
```

### 실행 결과

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2063.png)

## 값 타입 조회 예제

### jpaMain.java

```java
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
    member.getAddressHistory().add(new Address("old1", "street1", "12345"));
    member.getAddressHistory().add(new Address("old2", "street1", "12345"));

    em.persist(member);

    em.flush();
    em.clear();

    // SQL 쿼리 확인
    System.out.println("===============================");
    Member findMember = em.find(Member.class, member.getId());
    System.out.println("===============================");

    // 값 타입 조회
    List<Address> addressHistory = findMember.getAddressHistory();
    for (Address address : addressHistory){
        System.out.println("address = " + address.getCity());
    }

    // 값 타입  조회
    Set<String> favoriteFoods = findMember.getFavoriteFoods();
    for ( String favoriteFood : favoriteFoods){
        System.out.println("favoriteFood = " + favoriteFood);
    }

}
```

- 값 타입 컬렉션 지연 로딩 전략 사용 (default)
    - **@ElementCollection(fetch = FetchType.LAZY)**

### 실행 결과

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2064.png)

=> 컬렉션들은 지연로딩되는 것을 볼수있다. (컬렉션빼놓고 select 된다.)

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2065.png)

⇒ 조회 역시 잘 되는 것을 볼 수 있다.

---

## 값 타입 수정 예제

```java
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
        member.getAddressHistory().add(new Address("old1", "street1", "12345"));
        member.getAddressHistory().add(new Address("old2", "street1", "12345"));

        em.persist(member);

        em.flush();
        em.clear();

        // SQL 쿼리 확인
        System.out.println("===============================");
        Member findMember = em.find(Member.class, member.getId());
        System.out.println("===============================");

        /* 값 타입 수정 */
        // 수정하고자 하는 값 homeCity >>> newCity

        // findMember.getHomeAddress().setCity("newCity"); // >>> 이렇게 하면 안된다!!!!!

        Address a = findMember.getHomeAddress();
        // 아래와 같이 아예 갈아 끼어야 해!!!
        findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

        // 음식 : 치킨 -> 한식
        findMember.getFavoriteFoods().remove("치킨");
        findMember.getFavoriteFoods().add("한식");

        // 도시 : old1 > new1
        findMember.getAddressHistory().remove(new Address("old1", "street1", "12345"));
        findMember.getAddressHistory().add(new Address("new1", "street1", "12345"));
    }
```

**1. 임베디드 값 타입 수정**

- member 테이블만 update한다. 사실 member 엔티티를 수정하는 것과 같다.

**2. 기본값 타입 컬렉션 수정**

- 탕수육을 치킨으로 변경하려면 탕수육을 제거하고 치킨을 추가해야 한다.
- 자바의 String타입은 수정 불가능

**3. 임베디드 값 타입 컬렉션 수정**

- 값 타입은 불변해야 한다. 따라서 컬렉션에서 기존 주소를 삭제하고 새로운 주소를 등록했다.

값타입은 equals, hashcode 꼭 구현!

`참고`

- 값 타입 컬렉션은 영속성 전이(Cascade) + 고아 객체 제거 기능을 필수로 가진다고 볼 수 있다.
    - 값타입 컬렉션은 다른 테이블인데도 불구하고 라이프사이클이 같이 돌아감(생명주기가 member에 소속된것)

## 값 타입 컬렉션의 제약사항

- 값 타입은 엔티티와 다르게 식별자 개념이 없다.
- 값은 변경하면 추적이 어렵다.
- 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다.
- 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본키를 구성해야 함: null 입력X, 중복 저장X

## 값 타입 컬렉션 대안

- 실무에서는 상황에 따라 값 타입 컬렉션 대신에 일대다 관계를 고려
- 일대다 관계를 위한 엔티티를 만들고, 여기에서 값 타입을 사용
- 영속성 전이(Cascade) + 고아 객체 제거를 사용해서 값 타입 컬렉션처럼 사용
- EX) AddressEntity

### AddressEntity.java

```java
package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Address address;

    // 기본 생성자
    public AddressEntity() {
    }

    // constructor
    public AddressEntity(Address address) {
        this.address = address;
    }

    public AddressEntity(String city, String street, String zipcode) {
        this.address = new Address(city, street, zipcode);
    }

    / 이하, getter & setter/
}
```

### Member.java

```java
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

    /* 값 타입 컬렉션 대안 */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

    /* 객체 지향 모델링 >>> 객체의 참조와 테이블의 외래 키를 매핑 */
    @ManyToOne(fetch = FetchType.EAGER) /* 다대일 매핑, FetchType.EAGER >>> 즉시 로딩 */
    @JoinColumn(name = "TEAM_ID") /* 조인 컬럼은 외래 키를 매핑할 때 사용한다. (name 속성은 매핑할 외래 키 이름을 지정한다. */
    private Team team; // 연관관계 주인 ㅇ ==> 주인 아닌 놈은 Team.java에 있다.

    /* getter & setter */

}
```

- /* 값 타입 컬렉션 대안 */ 부분을 보자

### jpaMain.java

```java
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

    }
```

### 실행 결과

![Untitled](%E1%84%8E%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5(ORM&JPA)%209f153f5a74004275a96b628020224f80/Untitled%2066.png)

- 주소 테이블에 ID란 것이 생긴 것을 볼 수 있다.
    - 자체적인 ID가 있다는 것은 값 타입이 아닌 엔티티라는 것을 의미한다.
        - 이렇게 되면 이제 마음대로 수정할 수 있다.
    - 또한, 외래키로 MEMBER_ID를 가져간다.

## 정리

- 엔티티 타입의 특징
    - 식별자 O
    - 생명 주기 관리
    - 공유
- 값 타입의 특징
    - 식별자 X
    - 생명 주기를 엔티티에 의존
    - 공유하지 않는 것이 안전(복사해서 사용)
    - 불변 객체로 만드는 것이 안전
    

값 타입은 정말 값 타입이라 판단될 때만 사용

엔티티와 값 타입을 혼동해서 엔티티를 값 타입으로 만들면 안됨

식별자가 필요하고, 지속해서 값을 추적, 변경해야 한다면 그것은 값 타입이 아닌 엔티티

---

# 섹션 10 - ****객체지향 쿼리 언어1 - 기본 문법****

## 📝 객체지향 쿼리 언어 소개

JPA는 다양한 쿼리 방법을 지원한다.

- **JPQL >>> 중요**
- JPA Criteria
- **QueryDSL >>> 중요**
- 네이티브 SQL
- JDBC API 직접 사용, MyBatis, SpringJdbcTemplate 함께 사용

### 📜 JPQL

- JPA를 사용하면 엔티티 객체를 중심으로 개발할 수 있다.
    - SQL은 테이블 중심 / JPA는 엔티티 객체 중심!!!
- 문제는 검색을 할 때도 **테이블이 아닌 엔티티 객체를 대상으로 검색해야 한다는 것이다.**
- 하지만 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능하다.
- 따라서 애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요하다.
- JPA는 SQL을 추상화한 **JPQL**이라는 객체 지향 쿼리 언어 제공한다.
    - Java Persistence Query Language
- 문법은 SQL과 유사하며, 둘의 가장 큰 차이점은 **JPQL은 엔티티 객체를 대상으로, SQL은 데이터베이스 테이블을 대상으로 쿼리**한다는 것이다.
- 즉, **JPQL을 한마디로 정의하면 객체 지향 SQL**이라고 말할 수 있다.

```java
//검색String jpql = "select m From Member m where m.name like ‘%hello%'";
List<Member> result = em.createQuery(jpql, Member.class).getResultList();
```

### 📜 QueryDSL

- 문자가 아닌 자바 코드로 JPQL을 작성할 수 있음
- JPQL 빌더 역할
- **컴파일 시점에 문법 오류를 찾을 수 있음**
- **동적쿼리 작성 편리함**
- **단순하고 쉬움**
- **실무 사용 권장**

```java
// JPQL
// select m from Member m where m.age > 18
// JPAFactoryQuery query = new JPAQueryFactory(em);
QMember m = QMember.member;

List<Member> list =
	query.selectFrom(m)
		.where(m.age.get(18))
		.orderBy(m.name.desc())
		.fetch();
```

## 📝 JPQL 기본

- JPQL은 객체지향 쿼리 언어다. 따라서 테이블을 대상으로 쿼리하는 것이 아니라 엔티티 객체를 대상으로 쿼리한다.
- **JPQL은 SQL을 추상화해서 특정 데이터베이스 SQL에 의존하지 않는다.**
- **JPQL은 결국 SQL로 변환된다.**

### 📜 JPQL 문법

- ex) select m from Member as m where m.age > 18
- 엔티티와 속성은 대소문자 구분 O (Member, age)
- JPQL 키워드는 대소문자 구분 X (SELECT, FROM, where)
- 테이블 이름이 아닌 엔티티 이름 사용(Member)
- **별칭은 필수(m)** (as는 생략가능)

![https://blog.kakaocdn.net/dn/bJc0wk/btrDKH3GOtp/OkAy9UHcAF3ttuZ7NlEhp0/img.png](https://blog.kakaocdn.net/dn/bJc0wk/btrDKH3GOtp/OkAy9UHcAF3ttuZ7NlEhp0/img.png)

### 📜 TypeQuery, Query

- TypeQuery: 반환 타입이 명확할 때 사용
- Query: 반환 타입이 명확하지 않을 때 사용

```java
TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
Query query = em.createQuery("SELECT m.username, m.age from Member m");
```

### 📜 결과 조회 API

- query.getResultList(): **결과가 하나 이상일 때**, 리스트 반환
    - 결과가 없으면 빈 리스트 반환
- query.getSingleResult(): **결과가 정확히 하나**, 단일 객체 반환
    - 결과가 없으면: javax.persistence.NoResultException
    - 둘 이상이면: javax.persistence.NonUniqueResultException

### 📜 파라미터 바인딩 - 이름 기준, 위치 기준

```java
// 이름 기준
Member query = em.createQuery("SELECT m FROM Member m where m.username = :username", Member.class);
query.setParameter("username", usernameParam);
```

```java
// 위치 기준
Member query = em.createQuery("SELECT m FROM Member m where m.username = ?1", Member.class);
query.setParameter(1, usernameParam);
```

> 참고: 위치 기반 파라미터 바인딩은 왠만하면 사용하지 않도록 하자
> 

## 📝 프로젝션

- SELECT 절에 조회할 대상을 지정하는 것
- 프로젝션 대상: 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자 등 기본 데이터 타입)
    - SELECT **m** FROM Member m -> 엔티티 프로젝션
    - SELECT **m.team** FROM Member m -> 엔티티 프로젝션
    - SELECT **m.address** FROM Member m -> 임베디드 타입 프로젝션
    - SELECT **m.username, m.age** FROM Member m -> 스칼라 타입 프로젝션
- **DISTINCT로 중복 제거 가능**

### 📜 프로젝션 여러 값 조회

- SELECT **m.username, m.age** FROM Member m
- 1. Query 타입으로 조회
- 2. Object[] 타입으로 조회
- 3. new 명령어로 조회
    - 단순 값을 DTO로 바로 조회
        - SELECT **new** jpabook.jpql.UserDTO(m.username, m.age) FROM Member m
        - 패키지 명을 포함한 전체 클래스 명 입력
    - 순서와 타입이 일치하는 생성자 필요

## 📝 페이징

- JPA는 페이징을 다음 두 API로 추상화
- **setFirstResult**(int startPosition) : 조회 시작 위치(0부터 시작)
- **setMaxResults**(int maxResult) : 조회할 데이터 수

```java
//페이징 쿼리String jpql = "select m from Member m order by m.name desc";
List<Member> resultList = em.createQuery(jpql, Member.class)
	.setFirstResult(10)
	.setMaxResults(20)
	.getResultList();
```

## 📝 조인

- 내부 조인: SELECT m FROM Member m **[INNER] JOIN** m.team t
- 외부 조인: SELECT m FROM Member m **LEFT [OUTER] JOIN** m.team t
- 세타 조인: SELECT count(m) FROM Member m, Team t WHERE m.username = t.name

### 📜 ON

- ON절을 활용한 조인(JPA 2.1부터 지원)
    - **1. 조인 대상 필터링**
        - 예) 회원과 팀을 조인하면서, 팀 이름이 A인 팀만 조인
        - **JPQL**: SELECT m, t FROM Member m LEFT JOIN m.team t **on** t.name = 'A'
        - **SQL**: SELECT m.*, t.* FROM Member m LEFT JOIN Team t **ON** m.TEAM_ID=t.id and t.name='A'
    - **2. 연관관계 없는 엔티티 외부 조인(하이버네이트 5.1부터)**
        - 예) 회원의 이름과 팀의 이름이 같은 대상 외부 조인
        - **JPQL**: SELECT m, t FROM Member m LEFT JOIN Team t **on** m.username = t.name
        - **SQL**: SELECT m.*, t.* FROM Member m LEFT JOIN Team t **ON** m.username = t.name

## 📝 서브 쿼리

JPA에서도 SQL처럼 서브 쿼리를 사용할 수 있다.

- 나이가 평균보다 많은 회원
    - select m from Member m where m.age > **(select avg(m2.age) from Member m2)**
- 한 건이라도 주문한 고객
    - select m from Member m where **(select count(o) from Order o where m = o.member)** > 0

### 📜 서브 쿼리 지원 함수

- [NOT] EXISTS (subquery): 서브쿼리에 결과가 존재하면 참이 된다.
    - ex) select m from Member m where **exists** (select t from m.team t where t.name = ‘팀A')
- ALL: 모두 만족하면 참이 된다.
    - ex) select o from Order o where o.orderAmount > **ALL** (select p.stockAmount from Product p)
- ANY, SOME: 같은 의미, 조건을 하나라도 만족하면 참이 된다.
    - ex) select m from Member m where m.team = **ANY** (select t from Team t)
- [NOT] IN (subquery): 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참이 된다.

### 📜 JPA 서브 쿼리 한계

- JPA 표준 스펙 상으로는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
- 하이버네이트를 사용하면 SELECT 절도 가능
- FROM 절의 서브 쿼리는 현재 JPQL에서 불가능
    - 조인으로 풀 수 있으면 풀어서 해결

## 📝 JPQL 타입 표현

- 문자: ‘HELLO’, ‘She’’s’
- 숫자: 10L(Long), 10D(Double), 10F(Float)
- Boolean: TRUE, FALSE
- ENUM: jpabook.MemberType.Admin (패키지명 포함)
- 엔티티 타입: TYPE(m) = Member (상속 관계에서 사용)

### 📜 JPQL 기타

- SQL에서 사용하는 대부분 문법 사용 가능
- EXISTS, IN
- AND, OR, NOT
- =, >, >=, <, <=, <>
- BETWEEN, LIKE, **IS NULL**

## 📝 조건식

**기본 CASE 식**

```sql
select case when m.age <= 10 then '학생요금'when m.age >= 60 then '경로요금' else '일반요금'end from Member m
```

**단순 CASE 식**

```sql
select case t.name
		when '팀A' then '인센티브110%' when '팀B' then '인센티브120%' else '인센티브105%'end from Team t
```

- **COALESCE**: 하나씩 조회해서 null이 아니면 반환
    - 사용자 이름이 없으면 '이름 없는 회원'을 반환
    - select **coalesce(m.username,'이름 없는 회원')** from Member m
- **NULLIF**: 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
    - 사용자 이름이 ‘관리자’면 null을 반환하고 나머지는 본인의 이름을 반환
    - select **NULLIF(m.username, '관리자')** from Member m

---

## 📝 JPQL 기본 함수

- CONCAT
- SUBSTRING
- TRIM
- LOWER, UPPER
- LENGTH
- LOCATE
- ABS, SQRT, MOD
- SIZE, INDEX(JPA 용도)

### 📜 사용자 정의 함수 호출

- 하이버네이트는 사용 전 **방언**에 추가해야 한다.
    - 사용하는 DB 방언을 상속받고, 사용자 정의 함수를 등록한다.
    - select function('group_concat', i.name) from Item i

---

# 섹션 11 - ****객체지향 쿼리 언어2 - 중급 문법****

## 📝 JPQL - 경로 표현식

- 경로 표현식 > **점(.)을 찍어 객체 그래프를 탐색하는 것**
- **상태 필드**(state field): 단순히 값을 저장하기 위한 필드(ex: m.username)
    - 경로 탐색의 끝 / 탐색 X
- **연관 필드**(association field): 연관관계를 위한 필드
    - **단일 값 연관 필드**: @ManyToOne, @OneToOne, 대상이 엔티티(ex: m.team)
        - **묵시적 내부 조인(inner join) 발생**, 탐색 O
    - **컬렉션 값 연관 필드**: @OneToMany, @ManyToMany, 대상이 컬렉션(ex: m.orders)
        - **묵시적 내부 조인(inner join) 발생**, 탐색 X
        - FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능

### 📜 명시적 조인, 묵시적 조인

- 명시적 조인: join 키워드 직접 사용
    - select m from Member m **join m.team t**
- 묵시적 조인: 경로 표현식에 의해 묵시적으로 SQL 조인 발생(내부 조인만 가능)
    - select **m.team** from Member m

### 📜 경로 표현식 - 예제

- select o.member.team from Order o -> 성공
- select t.members from Team -> 성공
- select t.members.username from Team t -> 실패
    - 팀에 해당하는 멤버들은 일대다 관계이므로 대상이 컬렉션
    - 따라서, 탐색 불가
- select m.username from Team t join t.members m -> 성공
    - 명시적 조인을 사용해서 성공

### 📜 경로 탐색을 사용한 묵시적 조인 시 주의사항

- 항상 내부 조인
- 컬렉션은 경로 탐색의 끝
    - 만약, 경로 탐색을 하고 싶다면?
        - **명시적 조인을 통해 별칭을 얻어야 함**
- 경로 탐색은 주로 SELECT, WHERE 절에서 사용하지만, 묵시적 조인으로 인해 SQL

의 FROM (JOIN) 절에 영향을 줌

### 📜 실무 조언

- 묵시적 조인은 조인이 일어나는 상황을 한눈에 파악하기 어려움
- 조인은 SQL 튜닝에 중요 포인트
- **가급적 묵시적 조인 대신에 명시적 조인을 사용하자**

---

## 📝 JPQL - 페치 조인(fetch join)

**페치 조인은 실무에서 매우 중요한 요소이다.**

- SQL 조인 종류 X
- JPQL에서 **성능 최적화**를 위해 제공하는 기능
- 연관된 엔티티나 컬렉션을 **SQL 한 번에 함께 조회**하는 기능
    - 페치 조인을 하지 않으면, 여러번 조회해야 하는 비효율적인 작업이 수행된다.
- join fetch 명령어 사용
- 페치 조인 ::= [ LEFT [OUTER] | INNER ] JOIN FETCH 조인경로

### 📜 엔티티 페치 조인

- 회원을 조회하면서 연관된 팀도 함께 조회(SQL 한 번에)
- SQL을 보면 회원 뿐만 아니라 **팀(T.*)**도 함께 **SELECT**
- **[JPQL]** select **m** from Member m **join fetch** m.team
- **[SQL]** SELECT **M.*, T.*** FROM MEMBER M **INNER JOIN TEAM T** ON M.TEAM_ID=T.ID

![https://blog.kakaocdn.net/dn/sLYk0/btrDWEru1Wi/2YCjX9Jkp2TRAdCBVL6Ok0/img.png](https://blog.kakaocdn.net/dn/sLYk0/btrDWEru1Wi/2YCjX9Jkp2TRAdCBVL6Ok0/img.png)

### 📜 페치 조인 사용 코드

```java
String jpql = "select m from Member m join fetch m.team";
List<Member> members = em.createQuery(jpql, Member.class).getResultList();

for (Member member : members) {
	//페치 조인으로 회원과 팀을 함께 조회해서 지연 로딩X
	System.out.println("username = " + member.getUsername() + ", " +
		"teamName = " + member.getTeam().name());
}
```

```xml
username = 회원1, teamname = 팀A
username = 회원2, teamname = 팀A
username = 회원3, teamname = 팀B
```

### 📜 컬렉션 페치 조인

- 일대다 관계, 컬렉션 페치 조인
- **[JPQL]** select t from Team t **join fetch t.members** where t.name = ‘팀A'
- **[SQL]** SELECT T.*, **M.*** FROM TEAM T INNER JOIN MEMBER M ON T.ID=M.TEAM_ID WHERE T.NAME = '팀A'

![https://blog.kakaocdn.net/dn/cuRWjd/btrDYdzX3HN/Ho2lzLcQAaZB5q9RIvmzd0/img.png](https://blog.kakaocdn.net/dn/cuRWjd/btrDYdzX3HN/Ho2lzLcQAaZB5q9RIvmzd0/img.png)

### 📜 컬렉션 페치 조인 사용 코드

```java
String jpql = "select t from Team t join fetch t.members where t.name = '팀A'"
List<Team> teams = em.createQuery(jpql, Team.class).getResultList();

for(Team team : teams) {
	System.out.println("teamname = " + team.getName() + ", team = " + team);
	for (Member member : team.getMembers()) {
		//페치 조인으로 팀과 회원을 함께 조회해서 지연 로딩 발생 안함
		System.out.println(“-> username = " + member.getUsername()+ ", member = " + member);
	}
}
```

```xml
teamname = 팀A, team = Team@0x100
-> username = 회원1, member = Member@0x200
-> username = 회원2, member = Member@0x300
teamname = 팀A, team = Team@0x100
-> username = 회원1, member = Member@0x200
-> username = 회원2, member = Member@0x300
```

### 📜 페치 조인과 DISTINCT

- SQL의 DISTINCT는 중복된 결과를 제거하는 명령
    - SQL에 DISTINCT를 추가해도 데이터가 다르므로 결과적으로 중복제거 실패

![https://blog.kakaocdn.net/dn/yiyt2/btrDWD7dtqA/rXEvFnvD3U9CFfZLKDqaUk/img.png](https://blog.kakaocdn.net/dn/yiyt2/btrDWD7dtqA/rXEvFnvD3U9CFfZLKDqaUk/img.png)

- JPQL의 DISTINCT는 2가지 기능 제공
    - 1. SQL에 DISTINCT를 추가
    - 2. 애플리케이션에서 엔티티 중복 제거
        - DISTINCT가 추가로 애플리케이션에서 중복 제거시도
        - 같은 식별자를 가진 **Team 엔티티 제거**

![https://blog.kakaocdn.net/dn/lRgaD/btrDVopCJ5e/ZbrdUmkCwnREXj93cyAM5K/img.png](https://blog.kakaocdn.net/dn/lRgaD/btrDVopCJ5e/ZbrdUmkCwnREXj93cyAM5K/img.png)

### 📜 페치 조인과 일반 조인의 차이

- 일반 조인 실행시 연관된 엔티티를 함께 조회하지 않음
    - JPQL은 결과를 반환할 때 연관관계 고려 X
    - 단지 SELECT 절에 지정한 엔티티만 조회할 뿐
- 페치 조인을 사용할 때만 연관된 엔티티도 함께 조회**(즉시 로딩)**
- **페치 조인은 객체 그래프를 SQL 한번에 조회하는 개념**

### 📜 페치 조인의 한계

- **페치 조인 대상에는 별칭을 줄 수 없다.**
    - 하이버네이트에서는 가능하나 가급적 사용 X
- **둘 이상의 컬렉션은 페치 조인 할 수 없다.**
- **컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResults)를 사용할 수 없다.**
    - 일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능
    - 하이버네이트는 경고 로그를 남기고 메모리에서 페이징(매우 위험)

### 📜 페치 조인의 특징

- 연관된 엔티티들을 SQL 한 번으로 조회 - 성능 최적화
- 엔티티에 직접 적용하는 글로벌 로딩 전략보다 우선함
    - 실무에서 글로벌 로딩 전략은 모두 지연 로딩
    - @OneToMany(fetch = FetchType.LAZY) //글로벌 로딩 전략
- 최적화가 필요한 곳은 페치 조인을 적용하자

### 📜 페치 조인 정리

- 모든 것을 페치 조인으로 해결할 수는 없음
- 페치 조인은 객체 그래프를 유지할 때 사용하면 효과적
- 여러 테이블을 조인해서 엔티티가 가진 모양이 아닌 전혀 다른 결과를 내야 하면, 페치 조인 보다는 일반 조인을 사용하고 필요한 데이터들만 조회해서 DTO로 반환하는 것이 효과적

---

## 📝 다형성 쿼리

### 📜 TYPE

![https://blog.kakaocdn.net/dn/2kJRl/btrDVjVHXZ7/wgAn5L5I7aqaPLDAXO7WRk/img.png](https://blog.kakaocdn.net/dn/2kJRl/btrDVjVHXZ7/wgAn5L5I7aqaPLDAXO7WRk/img.png)

- 조회 대상을 특정 자식으로 한정
- 예) Item 중에 Book, Movie를 조회해라
    - **[JPQL]** select i from Item i where **type(i)** IN (Book, Movie)
    - **[SQL]** select i from i where i.DTYPE in (‘B’, ‘M’)

### 📜 TREAT(JPA 2.1)

- 자바의 타입 캐스팅과 유사
- 상속 구조에서 부모 타입을 특정 자식 타입으로 다룰 때 사용
- FROM, WHERE, SELECT(하이버네이트 지원) 사용
- 예) 부모인 Item과 자식 Book이 있다.
    - **[JPQL]** select i from Item i where **treat**(i as Book).auther = ‘kim’
    - **[SQL]** select i.* from Item i where i.DTYPE = ‘B’ and i.auther = ‘kim’

---

## 📝 JPQL - 엔티티 직접 사용

### 📜 기본 키 값

- JPQL에서 엔티티를 직접 사용하면 SQL에서 해당 엔티티의 기본 키 값을 사용
- **[JPQL]**
    - select **count(m.id)** from Member m //엔티티의 아이디를 사용
    - select **count(m)** from Member m //엔티티를 직접 사용
- **[SQL]**(위의 JPQL 둘 다 같은 다음 SQL 실행) select count(m.id) as cnt from Member m

**엔티티를 파라미터로 전달**

```java
String jpql = "select m from Member m where m = :member";
List resultList = em.createQuery(jpql).setParameter("member", member).getResultList();
```

**식별자를 직접 전달**

```java
String jpql = "select m from Member m where m.id = :memberId";
List resultList = em.createQuery(jpql).setParameter("memberId", memberId).getResultList();
```

**실행된 SQL**

```sql
select m.* from Member m where m.id=?
```

### 📜 외래 키 값

**엔티티를 파라미터로 전달**

```java
Team team = em.find(Team.class, 1L);
String qlString = "select m from Member m where m.team = :team";
List resultList = em.createQuery(qlString).setParameter("team", team).getResultList();
```

**식별자를 직접 전달**

```java
String qlString = "select m from Member m where m.team.id = :teamId";
List resultList = em.createQuery(qlString).setParameter("teamId", teamId).getResultList();
```

**실행된 SQL**

```sql
select m.* from Member m where m.team_id=?
```

---

## 📝 JPQL - Named 쿼리

- 미리 정의해서 이름을 부여해두고 사용하는 JPQL
- 정적 쿼리
- 어노테이션, XML에 정의
- **애플리케이션 로딩 시점에 초기화 후 재사용**
- **애플리케이션 로딩 시점에 쿼리를 검증**

### 📜 Named 쿼리 - 어노테이션

```java
@Entity@NamedQuery(
	name = "Member.findByUsername",
	query = "select m from Member m where m.username = :username")
public class Member {
	//...
}
```

```java
List<Member> resultList =
	em.createNamedQuery("Member.findByUsername", Member.class)
		.setParameter("username", "회원1")
		.getResultList();
```

### 📜 Named 쿼리 - XML

```xml
<persistence-unit name="jpabook" ><mapping-file>META-INF/ormMember.xml</mapping-file>
```

```xml
<?xml version="1.0" encoding="UTF-8"?><entity-mappings xmlns="http://xmlns.jcp.org/xml/ns/persistence/orm" version="2.1"><named-query name="Member.findByUsername"><query><![CDATA[
			select m
			from Member m
			where m.username = :username
		]]></query></named-query>

	<named-query name="Member.count"><query>select count(m) from Member m</query></named-query>

</entity-mappings>
```

### 📜 Named 쿼리 환경에 따른 설정

- XML이 항상 우선권을 가진다.
- 애플리케이션 운영 환경에 따라 다른 XML을 배포할 수 있다.

---

## 📝 JPQL - 벌크 연산

- 재고가 10개 미만인 모든 상품의 가격을 10% 상승하려면?
- JPA 변경 감지 기능으로 실행하려면 너무 많은 SQL 실행
    - 1. 재고가 10개 미만인 상품을 리스트로 조회한다.
    - 2. 상품 엔티티의 가격을 10% 증가한다.
    - 3. 트랜잭션 커밋 시점에 변경감지가 동작한다.
- 변경된 데이터가 100건이라면 100번의 UPDATE SQL 실행

### 📜 벌크 연산

- 쿼리 한 번으로 여러 테이블 로우 변경(엔티티)
- **executeUpdate()의 결과는 영향받은 엔티티 수 반환**
- **UPDATE, DELETE 지원**
- **INSERT(insert into .. select, 하이버네이트 지원)**

```java
String qlString = "update Product p " +
		"set p.price = p.price * 1.1 " +
		"where p.stockAmount < :stockAmount";
int resultCount = em.createQuery(qlString)
			.setParameter("stockAmount", 10)
			.executeUpdate();
```

### 📜 벌크 연산 - 주의

- 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리하므로 영속성 컨텍스트의 1차 캐시에 저장되어 있는 데이터와 데이터베이스에 저장되어 있는 데이터가 서로 다른 경우가 발생할 수 있음
- 두 가지 해결책
    - 1. 벌크 연산을 먼저 실행
    - 2. **벌크 연산 수행 후 영속성 컨텍스트 초기화**