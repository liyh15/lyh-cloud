package wibo.cloud.custom.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Classname RabbitDeadProduct
 * @Description TODO
 * @Date 2020/11/13 17:36
 * @Created by lyh
 */
public class RabbitDeadProduct {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("10.11.1.188");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("lyh");
        connectionFactory.setPassword("123456");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        // 创建一个持久化，非自动删除的direct交换器
        /**
         * exchangeDeclare有很多重载的方法
         *         channel.exchangeDeclare(String var1, String var2, boolean var3, boolean var4, boolean var5, Map<String, Object> var6) throws IOException;
         */
        channel.exchangeDeclare("DEADExchangeTest", "fanout", true, false, null);

        // TODO 变成死信的情况
        // TODO 消息被拒绝，Reject,Nack，并且设置requeue参数为false
        // TODO 消息过期
        // TODO 队列达到最大长度
        channel.queueDeclare("directQueueDEAD", true, false, false, null);
        channel.queueBind("directQueueDEAD", "DEADExchangeTest", "directQueueDEAD");
        TimeUnit.SECONDS.sleep(100000);
        channel.close();
        connection.close();
    }
}
