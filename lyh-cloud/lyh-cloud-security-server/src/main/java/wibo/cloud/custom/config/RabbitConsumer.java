package wibo.cloud.custom.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "direct")
public class RabbitConsumer {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver1-----------------------------------------  : " + hello);
    }
}
