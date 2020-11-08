package wibo.cloud.custom.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

public class RabbitMQTest {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.56.140");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        // 创建一个持久化，非自动删除的direct交换器
        /**
         * exchangeDeclare有很多重载的方法
         *         channel.exchangeDeclare(String var1, String var2, boolean var3, boolean var4, boolean var5, Map<String, Object> var6) throws IOException;
         */
        channel.exchangeDeclare("directExchangeTest", "direct", true, false, null);
        channel.queueDeclare("directQueueTest", true, false, false, null);
        channel.queueBind("directQueueTest", "directExchangeTest", "directQueueTest");
        String message = "hello world";
        for (int i = 0; i < 10000; i++) {
            channel.basicPublish("directExchangeTest", "directQueueTest", MessageProperties.PERSISTENT_TEXT_PLAIN, (i + "").getBytes());
        }
        channel.close();
        connection.close();
    }
}
