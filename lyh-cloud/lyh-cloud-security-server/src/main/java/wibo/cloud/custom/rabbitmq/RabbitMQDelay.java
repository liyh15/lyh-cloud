package wibo.cloud.custom.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitMQDelay {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.56.140");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("lyh");
        connectionFactory.setPassword("123456");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        // 创建一个持久化，非自动删除的direct交换器
        /**
         * exchangeDeclare有很多重载的方法
         *
         * channel.exchangeDeclare(String var1, String var2, boolean var3, boolean var4, boolean var5, Map<String, Object> var6) throws IOException;
         */

        channel.exchangeDeclare("DelayExchangeTest", "direct", true, false, null);


        channel.queueDeclare("directQueueDEAD", true, false, false, null);
        channel.queueBind("directQueueDEAD", "DEADExchangeTest", "directQueueDEAD");
        TimeUnit.SECONDS.sleep(100000);
        channel.close();
        connection.close();
    }
}
