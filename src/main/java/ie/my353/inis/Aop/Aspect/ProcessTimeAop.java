package ie.my353.inis.Aop.Aspect;

import ie.my353.inis.Aop.AnnotationInterfaces.Ordered;
import ie.my353.inis.Aop.AnnotationInterfaces.ProcessTimeDuration;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class ProcessTimeAop implements Ordered {



    @Around("@annotation(processTimeDuration)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint,
                          ProcessTimeDuration processTimeDuration) throws Throwable {
        LocalDateTime start = null;
        LocalDateTime end = null;

        try {

            start = LocalDateTime.now();
            //log.error("【Start at】->>>>>【" + start + "】Class name: 【"
                   // + proceedingJoinPoint.getSignature().getName()+" 】");
            Object proceed = proceedingJoinPoint.proceed();
            end = LocalDateTime.now();


            //log.error("【Stop at 】->>>>>【" + end + "】Class name: 【"
                    //+ proceedingJoinPoint.getSignature().getName()+" 】");
            return proceed;

        }finally {

            Duration duration = Duration.between(start,end);
/**
            String tableName="duration_" + proceedingJoinPoint.getSignature().getName();
            switch (tableName){
                case "duration_getKandP":
                    durationForKAndPRepository.saveKAndPDuration(new DurationForKAndP(duration.toMillis(), end));
                    break;
                case "duration_getAvailSlotsDateAndTime":
                    durationForAvailSlotRepository.saveDurationForAvailSlot(new DurationForAvailSlot(duration.toMillis(), end));
                    break;
                case "duration_getAvailSlot":
                    durationForTotalRepository.saveDurationForTotal(new DurationForTotal(duration.toMillis(), end));
                    break;
                case "duration_getAvailDate":
                    durationForAvailDateRepository.saveDurationForAvailDate(new DurationForAvailDate(duration.toMillis(), end));
            }
**/
            //log.error("Class ->>>>> 【" + proceedingJoinPoint.getSignature().getName()
                    //+ "】 Duration is: 【"
                   // + duration.toString()+"】");

        }
    }

    @Override
    public int getOrder() {
        return 1;
    }


}
