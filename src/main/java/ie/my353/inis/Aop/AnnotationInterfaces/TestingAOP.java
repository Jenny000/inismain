//package ie.my353.inis.Aop.AnnotationInterfaces;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//
///**
// * @author Administrator
// * date 24/02/2019.
// */
//@Aspect
//@Component
//@Slf4j
//public class TestingAOP {
//
//    @Before("@annotation(testing)")
//    public void proceeds(JoinPoint joinPoint, Testing testing) throws Throwable{
//
//        ServerHttpRequest arg = (ServerHttpRequest) joinPoint.getArgs()[0];
//        System.out.println(joinPoint.getArgs()[0].getClass());
//        System.out.println(arg.getHeaders().toSingleValueMap());
//
//
//
//
//    }
//}
