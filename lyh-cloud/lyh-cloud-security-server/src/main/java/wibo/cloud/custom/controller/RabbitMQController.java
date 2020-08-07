package wibo.cloud.custom.controller;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wibo.cloud.common.response.BaseResponse;

import java.util.*;

@RestController
@RefreshScope
@RequestMapping("/mq")
public class RabbitMQController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送RabbitMQ消息
     * @return
     */
    @RequestMapping(value = "snedMq", method = RequestMethod.GET)
    public BaseResponse sendMq(String mqMessage) {
        rabbitTemplate.convertAndSend("direct", mqMessage);
        return BaseResponse.DEFAULT;
    }

    /**
     * 发送RabbitMQ消息
     * @return
     */
    @RequestMapping(value = "snedMq2", method = RequestMethod.GET)
    public BaseResponse sendMq2(String mqMessage, String routKey) {
        rabbitTemplate.convertAndSend(routKey, mqMessage);
        return BaseResponse.DEFAULT;
    }

    /**
     * 发送RabbitMQ消息
     * @return
     */
    @RequestMapping(value = "snedMq3", method = RequestMethod.GET)
    public BaseResponse sendMq3(String mqMessage, String routKey, String exchange) {
        for (int i = 0; i < 200000; i ++) {
            rabbitTemplate.convertAndSend(exchange, routKey, mqMessage);
        }
        return BaseResponse.DEFAULT;
    }

    /**
     * 发送延迟队列消息
     * @return
     */
    @RequestMapping(value = "sendDelayMq", method = RequestMethod.GET)
    public BaseResponse sendDelayMq(String mqMessage, String routKey, String exchange) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //设置消息持久化（这个系统内部默认是持久化的）
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                // 设置延期时间
                message.getMessageProperties().setDelay(5000);
                return message;
            }
        };
        rabbitTemplate.convertAndSend(exchange, routKey, mqMessage, messagePostProcessor, correlationData);
        return BaseResponse.DEFAULT;
    }
}

