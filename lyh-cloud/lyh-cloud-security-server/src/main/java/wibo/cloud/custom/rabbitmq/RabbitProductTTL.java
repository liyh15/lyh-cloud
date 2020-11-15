package wibo.cloud.custom.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Classname RabbitProduction
 * @Description TODO
 * @Date 2020/11/13 11:34
 * @Created by lyh
 */
public class RabbitProductTTL {
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
         *         channel.exchangeDeclare(String var1, String var2, boolean var3, boolean var4, boolean var5, Map<String, Object> var6) throws IOException;
         */
        channel.exchangeDeclare("directExchangeTest", "direct", true, false, null);
        Map map = new HashMap();
        // 设置queue中消息的过期时间
        map.put("x-message-ttl",20000);
        // 设置队列未访问时的过期时间
        map.put("x-expires",100000);
        // 设置队列的死性队列
        map.put("x-dead-letter-exchange", "DEADExchangeTest");
        channel.queueDeclare("directQueueTTL", true, false, false, map);
        channel.queueBind("directQueueTTL", "directExchangeTest", "directQueueTTL");
        String message = "hello world";

        channel.basicPublish("directExchangeTest", "directQueueTTL",true, MessageProperties.PERSISTENT_TEXT_PLAIN, (message).getBytes());

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int i, String s, String s1, String s2, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                System.out.println(new String(bytes));
            }
        });
        TimeUnit.SECONDS.sleep(100000);
        channel.close();
        connection.close();
    }
}
