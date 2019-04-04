//package ie.my353.inis.Aop;
//
//import ie.my353.my353inis.Aop.AnnotationInterfaces.RequestCount;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//
///**
// * @author Administrator
// * date 23/02/2019.
// */
//@Aspect
//@Component
//@Slf4j
//public class RequestCountAop implements Ordered {
//
//    @Around("@annotation(requestCount)")
//    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, RequestCount requestCount) throws Throwable {
//
//        try {
//
//            Object proceed = proceedingJoinPoint.proceed();
//
//            System.out.println(proceedingJoinPoint.getSignature().getName());
//
//            System.out.println(Arrays.stream(proceedingJoinPoint.getArgs()).map(x -> (List) x).mapToLong(Collection::size).sum());
//
//
//            return proceed;
//        }finally {
//
//        }
//
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
