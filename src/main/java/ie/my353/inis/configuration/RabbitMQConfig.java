package ie.my353.inis.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class RabbitMQConfig  implements RabbitListenerConfigurer {

    final static String QUEUE_NAME = "slot"; //队列名称
    //final static String QUEUE_NAME = "slot1"; //队列名称
    final static String EXCHANGE_NAME = "slotDirect"; //交换器名称
    @Bean
    public Queue SlotQueue() {
        Map<String, Object> arguments = new HashMap<>();

        arguments.put("x-message-ttl",58000);

        // 声明队列 参数一：队列名称；参数二：是否持久化
        return new Queue(RabbitMQConfig.QUEUE_NAME, false,false,false,arguments);
    }
    // 配置默认的交换机，以下部分都可以不配置，不设置使用默认交换器（AMQP default）
    @Bean
    DirectExchange directExchange() {
        // 参数一：交换器名称；参数二：是否持久化；参数三：是否自动删除消息
        return new DirectExchange( RabbitMQConfig.EXCHANGE_NAME, false, false);
    }
    // 绑定“direct”队列到上面配置的“mydirect”路由器
    @Bean
    Binding bindingExchangeDirectQueue(Queue directQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue).to(directExchange).with( RabbitMQConfig.QUEUE_NAME);
    }



    @Bean
   public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
       return new Jackson2JsonMessageConverter();
  }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public MessageConverter messageConverter(){
        ObjectMapper objectMapper= new ObjectMapper().findAndRegisterModules();

        return new Jackson2JsonMessageConverter(objectMapper);
    }





}
