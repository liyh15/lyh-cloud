package wibo.cloud.custom.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConsumer {

    private static ConnectionFactory connectionFactory = null;

    public static String a = "BBB";

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        RabbitMQConsumer.consumer();
    }

    /**
     * 消费消息并拒绝消息，如果消费者在消费完消息后还未提交标签就断开则有可能导致一个消息被重复消费了两次
     * 所以后端需要给消息添加幂等性校验，例如给消费过的消息记录到一个列表里面
     */
    public static void consumerReject() throws URISyntaxException, IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        /**
         *  queue: QueueName, //队列名称
         *  durable: false, //队列是否持久化.false:队列在内存中,服务器挂掉后,队列就没了;true:服务器重启后,队列将会重新生成.注意:只是队列持久化,不代表队列中的消息持久化!!!!
         *  exclusive: false, //队列是否专属,专属的范围针对的是连接,也就是说,一个连接下面的多个信道是可见的.对于其他连接是不可见的.连接断开后,该队列会被删除.注意,不是信道断开,是连接断开.并且,就算设置成了持久化,也会删除.
         *  autoDelete: false, //如果所有消费者都断开连接了,是否自动删除.如果还没有消费者从该队列获取过消息或者监听该队列,那么该队列不会删除.只有在有消费者从该队列获取过消息后,该队列才有可能自动删除(当所有消费者都断开连接,不管消息是否获取完)
         */
        channel.queueDeclare("Queue_Java", false, false, false, null);
        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body);
                System.out.println("Received22: " + message);
                // 消息拒绝
                try {
                    /**
                     * 第二个参数表示批量拒绝，如果为true表示当前消息之前的消息也会被拒绝，第三个表示拒绝时是否重回队列，为true表示
                     * 当拒绝的时候会重回队列,现在运行会一直重回队列并消费
                     */
                    channel.basicNack(envelope.getDeliveryTag(), false, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        // 关闭自动消息确认，autoAck = false
        channel.basicConsume("Queue_Java", false, consumer);
    }


    /**
     * 消费消息,只投递第十个标签
     */
    public static void consumer2() throws URISyntaxException, IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("Queue_Java", false, false, false, null);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("Received11: " + message);
                // 消息确认
                try {
                    /**
                     * 第一个参数表示投递标签，从1开始每次增加1，用于唯一标识信道的每次投递，
                     * 第二个参数表示是否进行消息的批量确认。若确认消息时开启批量确认，则投递标签小于当前消息投递标签的所有消息也都会进行确认。
                     */
                    // 投递标签“10”之前的消息进行批量确认
                    if(10L == envelope.getDeliveryTag()) {
                        try {
                            channel.basicAck(envelope.getDeliveryTag(), true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // 关闭自动消息确认，autoAck = false
        channel.basicConsume("Queue_Java", false, consumer);
    }

    /**
     * 消费消息
     */
    public static void consumer() throws URISyntaxException, IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        // channel.queueDeclare("Queue_Java", false, false, false, null);
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("已经处理完" + message);
                // 消息确认
                try {
                    /**
                     * 第一个参数表示投递标签，从1开始每次增加1，用于唯一标识信道的每次投递，
                     * 第二个参数表示是否进行消息的批量确认。若确认消息时开启批量确认，则投递标签小于当前消息投递标签的所有消息也都会进行确认。
                     */
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        // 关闭自动消息确认，autoAck = false
        channel.basicConsume("delay", false, consumer);
    }


    public static Connection getConnection() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("10.11.1.52");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        return connectionFactory.newConnection();
    }
}
