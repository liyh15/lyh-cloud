package wibo.cloud.custom.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitMQReject {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("10.11.1.188");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("lyh");
        connectionFactory.setPassword("123456");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(200);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("1  " + new String(body));
                // TODO 表示拒绝消息并重回队列
                // channel.basicReject(envelope.getDeliveryTag(), true);
                // TODO 表示拒绝消息并删除消息
                // channel.basicReject(envelope.getDeliveryTag(), false);
                // TODO 表示批量拒绝消息, 第二个参数为true
                channel.basicNack(envelope.getDeliveryTag(),true,true);
                // TODO 表示请求rabbitmq重新发送一遍未确认的消息，如果为true，则可能发送到不同的消费者，为false分配给之前相同的消费者
                //channel.basicRecover(true);
            }
        };
        channel.basicConsume("directQueueTest",false, consumer);
        TimeUnit.SECONDS.sleep(100000);
        // TODO 对于Connection而言，关闭之后会自动关闭channel
        channel.close();
        connection.close();
    }
}
