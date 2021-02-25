package wibo.cloud.custom.rabbitmq;

import com.rabbitmq.client.*;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitConfirm {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.126.129");
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

        channel.exchangeDeclare("ConfirmExchangeTest", "direct", true, false, null);

        channel.queueDeclare("directQueueConfirm", true, false, false, null);
        channel.queueBind("directQueueConfirm", "ConfirmExchangeTest", "directQueueConfirm");

        channel.confirmSelect();
        String message = "aaa";
        // TODO 获取下一条消息的编号
        System.out.println(channel.getNextPublishSeqNo());
        channel.basicPublish("ConfirmExchangeTest", "directQueueConfirm1111",true, MessageProperties.PERSISTENT_TEXT_PLAIN, (message).getBytes());
        System.out.println(channel.getNextPublishSeqNo());
        channel.basicPublish("ConfirmExchangeTest", "directQueueConfirm1111",true, MessageProperties.PERSISTENT_TEXT_PLAIN, (message).getBytes());
        System.out.println(channel.getNextPublishSeqNo());
        channel.basicPublish("ConfirmExchangeTest", "directQueueConfirm1111",true, MessageProperties.PERSISTENT_TEXT_PLAIN, (message).getBytes());

        // TODO 这个只能保证消息发送到交换器，需要和mandatory配合保证消息发送到队列，mandatory就是basicPublish第三个参数
       /* if (!channel.waitForConfirms()) {
            System.out.println("faild");
        }*/

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                // TODO 表示消息发送成功
                System.out.println("success" + l + b);
            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                // TODO 表示消息发送错误
                System.out.println("fail" + l + b);
            }
        });
        TimeUnit.SECONDS.sleep(100000);
        channel.close();
        connection.close();
    }
}
