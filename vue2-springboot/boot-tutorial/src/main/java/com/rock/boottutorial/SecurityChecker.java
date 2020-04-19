package com.rock.boottutorial;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// SecurityChecker 애스펙트는 일반적인 스프링 빈이다. 특별한 점은 @Aspect 어노테이션을 가지고 있다는 점이다.
// 스프링은 <aop:config>요소를 사용하는 XML로 정의된 AOP 설정뿐만 아니라 여기에서 사용한 방법인
// AspectJ 어노테이션 정의를 통한 APO 설정을 지원한다.
@Aspect
@Component
public class SecurityChecker {

    private static final Logger logger = LoggerFactory.getLogger(SecurityChecker.class);

    // SecurityChecker 애스펙트 내부에서 @SecurityCheck 어노테이션을 위한 @annotation PCD 와
    // @Pointcut어노테이션이 지정된 포인트컷 시그니처 checkMethodSecurity()를 생성한다
    @Pointcut("@annotation(SecurityCheck)")
    public void checkMethodSecurity() {}

    // checkSecurity() 어드바이스 내부에서 이 어드바이스가 checkMethodSecurity() 라는 포인트컷 표현식과
    // 함께 around device 임을 명시하기 위해 @Around 어노테이션을 사용한다.
    @Around("checkMethodSecurity()")
    public Object checkSecurity (ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("Checking method security ...");
        // TODO 보안검사 로직 구현
        Object result = joinPoint.proceed();
        return result;
    }
}
