package ie.my353.inis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Administrator
 * date 02/03/2019.
 * ProcessTimeEntityMQ(threadName=scheduling-1,
 *  getKandP=PT0S, getApps4DTAvailability=PT1.475S,
 *  getApps4DT=PT0S, getApps4DTArgMap=PT0S,
 *  singleThreadSCTask=PT1.514S, multipleThreadScTask=PT0S,
 *  recordTime=04/03/2019 16:04:30)
 *  [{"threadName":"scheduling-1","getKandP":0.869000000,"getApps4DTAvailability":4.857000000,"getApps4DT":0.0,"getApps4DTArgMap":0.0,"singleThreadSCTask":0.0,"multipleThreadScTask":7.200000000,"recordTime":"04/03/2019 16:40:30"},{"threadName":"ForkJoinPool.commonPool-worker-1","getKandP":0.869000000,"getApps4DTAvailability":4.811000000,"getApps4DT":0.0,"getApps4DTArgMap":0.0,"singleThreadSCTask":0.0,"multipleThreadScTask":0.0,"recordTime":"04/03/2019 16:40:30"}]
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "process_time")
public class ProcessTimeEntityMQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id;
    String threadName;
    Duration getKandP=Duration.ZERO;
    Duration getApps4DTAvailability;
    Duration getApps4DT;
    Duration getApps4DTArgMap;
    Duration getAppsNear;
    Duration getNearAppFunctionTask;
    Duration singleThreadSCTask =Duration.ZERO;
    Duration multipleThreadScTask= Duration.ZERO;
    String recordTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

    public ProcessTimeEntityMQ(String threadName, Duration getKandP, Duration getApps4DTAvailability, Duration getApps4DT, Duration getApps4DTArgMap, Duration singleThreadSCTask, Duration multipleThreadScTask, String recordTime) {
        this.threadName = threadName;
        this.getKandP = getKandP;
        this.getApps4DTAvailability = getApps4DTAvailability;
        this.getApps4DT = getApps4DT;
        this.getApps4DTArgMap = getApps4DTArgMap;
        this.singleThreadSCTask = singleThreadSCTask;
        this.multipleThreadScTask = multipleThreadScTask;
        this.recordTime = recordTime;
    }
}
