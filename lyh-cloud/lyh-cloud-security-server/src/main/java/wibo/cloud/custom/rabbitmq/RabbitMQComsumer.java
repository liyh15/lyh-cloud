package wibo.cloud.custom.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitMQComsumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.126.129");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("lyh");
        connectionFactory.setPassword("123456");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(2);
        Channel channel2 = connection.createChannel();
        channel2.basicQos(100);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("1  " + new String(body));
               /* try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        Consumer consumer2 = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("2  " + new String(body));
                System.out.println(envelope.getRoutingKey());
               /* try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // TODO 在一个通道下开启多个消费者在一次拉取下只会有一个消费者消费
        channel.basicConsume("directQueueDEAD",false, consumer2);
        channel.basicConsume("directQueueTest",false, consumer);
        TimeUnit.SECONDS.sleep(100000);
        channel.close();
        connection.close();
    }
}
