package ie.my353.inis.rabbitmq;


import com.fasterxml.jackson.databind.ObjectMapper;
import ie.my353.inis.entity.ProcessTimeEntityMQ;
import ie.my353.inis.entity.RequestCountEntityMQ;
import ie.my353.inis.entity.SlotList;
import ie.my353.inis.entity.Slots;
import ie.my353.inis.repository.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component

public class RabbitmqReceiver {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProcessTimeRepositoryImpl processTimeRepository;
    @Autowired
//    private RequestCountRepositoryImpl requestCountRepository;
    private RequestCountRepository requestCountRepository;
    @Autowired
    private SlotsRepositoryImpl slotsRepository;


    @RabbitListener(queues = "requestCount")
    public void rtLi(Message string) throws IOException {
        //RequestCountEntityMQ[] requestCountEntityMQ = objectMapper.readValue(string.getBody(), RequestCountEntityMQ[].class);
        RequestCountEntityMQ[] requestCountEntityMQ = objectMapper.readValue(string.getBody(), RequestCountEntityMQ[].class);

        System.out.println("RequestCount from MQ:::->>>" + requestCountEntityMQ);
        Arrays.stream(requestCountEntityMQ).forEach(System.out::println);
        requestCountRepository.saveAll(Arrays.asList(requestCountEntityMQ));

    }

    @RabbitListener(queues = "processTime")
    public void rti(Message string) throws IOException {

        ProcessTimeEntityMQ[] processTimeEntityMQS = objectMapper.readValue(string.getBody(), ProcessTimeEntityMQ[].class);
        for (ProcessTimeEntityMQ processTimeEntityMQ : processTimeEntityMQS) {
            System.out.println("processTime->>>>" + processTimeEntityMQ);
            processTimeRepository.saveProcessTime(processTimeEntityMQ);
        }
    }

    //todo 1 add spring filter for block ips 随机给条假信息
    //todo 2

    @RabbitListener(queues = "slotsList")
    public void saveSlotsListMQ(Message string) throws IOException {
        Slots[] slots = objectMapper.readValue(string.getBody(), Slots[].class);
        SlotList slotList = new SlotList();
        slotList.setSlotsList(Arrays.asList(slots));
         System.out.println("mq    "  + slotList);
        slotsRepository.saveSlots(slotList);


    }


}
