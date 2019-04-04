package ie.my353.inis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Administrator
 * date 02/03/2019.
 *
 * RequestCountEntityMQ(currentTotalCount=16, thisRequestCount=0, countTime=04/03/2019 16:02:32)
 * {"currentTotalCount":16,"thisRequestCount":0,"countTime":"04/03/2019 16:39:36"}
 *
 *
 * //    @RabbitListener(queues = "requestCount")
 *     public void rtLi(Message string) throws IOException {
 *
 *         RequestCountEntityMQ RequestCountEntityMQ = objectMapper.readValue(string.getBody(), RequestCountEntityMQ.class);
 *         System.out.println("RequestCount from MQ:::->>>" + RequestCountEntityMQ);
 *     }
 *
 * //    @RabbitListener(queues = "processTime")
 *     public void rti(Message string) throws IOException {
 *
 *         ProcessTimeEntityMQ[] processTimeEntityMQS = objectMapper.readValue(string.getBody(), ProcessTimeEntityMQ[].class);
 *         for (ProcessTimeEntityMQ processTimeEntityMQ : processTimeEntityMQS) {
 *             System.out.println("processTime->>>>" + processTimeEntityMQ);
 *         }
 *     }
 */
@Data
@Entity(name = "request_count")
@AllArgsConstructor
@NoArgsConstructor
public class RequestCountEntityMQ implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id;

    int currentTotalCount;
    long thisRequestCount;
    String countTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

    public RequestCountEntityMQ(int currentTotalCount, long thisRequestCount) {
        this.currentTotalCount = currentTotalCount;
        this.thisRequestCount = thisRequestCount;
    }
}
