package ie.my353.inis.rabbitmq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j

public class SlotCatAndTypeSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendSlotCatAndTypeToQueue(Set message){
        //参数一：交换器名称，可以省略（省略存储到AMQP default交换器）；参数二：路由键名称（direct模式下路由键=队列名称）；参数三：存储消息

        this.rabbitTemplate.convertAndSend("slot", message);
        //this.rabbitTemplate.convertAndSend("slot1", message);
        log.info("Direct 发送消息：" + message);
    }



}
