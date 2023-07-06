package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // 이것을 기재해줘야 AOP를 활용할 수 있다.
@Component
public class TimeTraceAop {
    /*
    @Around 활용하는 이유 >>> AOP를 통해 원하는 곳에 공통 관심 사항 적용해주려고
    @Around의 괄호 안에 있는 문법 의미 >>> 해당 패키지의 하위 목록에는 다 적용해주겠다.
    >>> 즉, 여기서는 hello.hellospring 패키지 안에 있는 것에 다 적용해주겠다!
     */
    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString()+ " " + timeMs +
                    "ms");
        }
    }
}
