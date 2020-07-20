package wibo.cloud.custom.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wibo.cloud.common.response.BaseResponse;

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
        rabbitTemplate.convertAndSend(exchange, routKey, mqMessage);
        return BaseResponse.DEFAULT;
    }
}
