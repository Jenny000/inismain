//package ie.my353.inis.Aop;
//
//import ie.my353.my353inis.Aop.AnnotationInterfaces.IpDetector;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.core.Ordered;
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
//public class IpDetectorAop implements Ordered {
//
//
//    @Around("@annotation(ipDetector)")
//    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, IpDetector ipDetector) throws Throwable{
//
//        Object[] args = proceedingJoinPoint.getArgs();
//        ServerHttpRequest arg = (ServerHttpRequest) proceedingJoinPoint.getArgs()[0];
//        args[1]="bcg";
//
//        System.out.println((String) args[1]);
//
////        System.out.println(arg.getHeaders().toSingleValueMap());
//
//
//        Object proceed = proceedingJoinPoint.proceed(args);
//
//        return proceed;
//    }
//
//
//
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
