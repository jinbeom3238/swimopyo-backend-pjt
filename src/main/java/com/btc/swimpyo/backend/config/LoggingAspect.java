package com.btc.swimpyo.backend.config;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.stream.Collectors;
import com.google.common.base.Joiner;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet().stream()
                .map(entry -> String.format("%s -> (%s)",
                        entry.getKey(), Joiner.on(",").join(entry.getValue())))
                .collect(Collectors.joining(", "));
    }
//
//    @Pointcut("execution(* com.btc.swimpyo.backend.controller.*.*(..))")
//    public void onRequest() {
//
//    }
//
//    @Around("com.btc.swimpyo.backend.config.LoggingAspect.onRequest()") // 4
//    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
//        HttpServletRequest request = // 5
//                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//
//        Map<String, String[]> paramMap = request.getParameterMap();
//        String params = "";
//        if (paramMap.isEmpty() == false) {
//            params = " [" + paramMapToString(paramMap) + "]";
//        }
//
//        long start = System.currentTimeMillis();
//        try {
//            return pjp.proceed(pjp.getArgs()); // 6
//        } finally {
//            long end = System.currentTimeMillis();
//            logger.debug("AOP Request: {} {}{} < {} ({}ms)", request.getMethod(), request.getRequestURI(),
//                    params, request.getRemoteHost(), end - start);
//        }
////    }
    @Around("execution(* com.btc.swimpyo.backend.controller..*(..))")
    public Object doTransactionController(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // @Before 수행
            logger.info("[AOP befor 시작] {}", joinPoint.getSignature());
            // @Before 종료
            HttpServletRequest request = // 5
                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            Map<String, String[]> paramMap = request.getParameterMap();
            String params = "";
            if (paramMap.isEmpty() == false) {
                params = " [" + paramMapToString(paramMap) + "]";
            }
            logger.info("[AOP Controller Catch Request Parameter] {}", params);
            // Target 메서드 호출
            Object result = joinPoint.proceed();
            // Target 메서드 종료

            // @AfterReturning 수행
            //logger.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            // @AfterReturning 종료

            // 값 반환
            return result;
        } catch (Exception e) {
            // @AfterThrowing 수행
            logger.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
            // @AfterThrowing 종료
        } finally {
            //@ After 수행
            // logger.info("[리소스 릴리즈] {}", joinPoint.getSignature());
            //@ After 종료
        }
    }


    @Around("execution(* com.btc.swimpyo.backend.service..*(..))")
    public Object doTransactionService(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // @Before 수행
            logger.info("[AOP Service befor 시작] {}", joinPoint.getSignature());
            // @Before 종료
            HttpServletRequest request = // 5
                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            Map<String, String[]> paramMap = request.getParameterMap();
            String params = "";
            if (paramMap.isEmpty() == false) {
                params = " [" + paramMapToString(paramMap) + "]";
            }
            logger.info("[AOP Service Catch Request Parameter] {}", params);
            // Target 메서드 호출
            Object result = joinPoint.proceed();
            // Target 메서드 종료

            // @AfterReturning 수행
            //logger.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            // @AfterReturning 종료

            // 값 반환
            return result;
        } catch (Exception e) {
            // @AfterThrowing 수행
            logger.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
            // @AfterThrowing 종료
        } finally {
            //@ After 수행
            // logger.info("[리소스 릴리즈] {}", joinPoint.getSignature());
            //@ After 종료
        }
    }
}
