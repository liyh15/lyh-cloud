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
        connectionFactory.setHost("192.168.126.129");
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
        /**
         * 参数介绍：
         * 1、name: 队列的名称；
         * 2、actualName: 队列的真实名称，默认用name参数，如果name为空，则根据规则生成一个；
         * 3、durable: 是否持久化；
         * 4、exclusive: 是否独享、排外的；
         * 5、autoDelete: 是否自动删除；
         * 6、arguments：队列的其他属性参数，有如下可选项，可参看图2的arguments：
         * （1）x-message-ttl：消息的过期时间，单位：毫秒；
         * （2）x-expires：队列过期时间，队列在多长时间未被访问将被删除，单位：毫秒；
         * （3）x-max-length：队列最大长度，超过该最大值，则将从队列头部开始删除消息；
         * （4）x-max-length-bytes：队列消息内容占用最大空间，受限于内存大小，超过该阈值则从队列头部开始删除消息；
         * （5）x-overflow：设置队列溢出行为。这决定了当达到队列的最大长度时消息会发生什么。有效值是drop-head、reject-publish或reject-publish-dlx。仲裁队列类型仅支持drop-head；
         * （6）x-dead-letter-exchange：死信交换器名称，过期或被删除（因队列长度超长或因空间超出阈值）的消息可指定发送到该交换器中；
         * （7）x-dead-letter-routing-key：死信消息路由键，在消息发送到死信交换器时会使用该路由键，如果不设置，则使用消息的原来的路由键值
         * （8）x-single-active-consumer：表示队列是否是单一活动消费者，true时，注册的消费组内只有一个消费者消费消息，其他被忽略，false时消息循环分发给所有消费者(默认false)
         * （9）x-max-priority：队列要支持的最大优先级数;如果未设置，队列将不支持消息优先级；
         * （10）x-queue-mode（Lazy mode）：将队列设置为延迟模式，在磁盘上保留尽可能多的消息，以减少RAM的使用;如果未设置，队列将保留内存缓存以尽可能快地传递消息；
         * （11）x-queue-master-locator：在集群模式下设置镜像队列的主节点信息。
         *   //声明队列
         *                     channel.QueueDeclare
         *                     (
         *                         queue: QueueName, //队列名称
         *                         durable: false, //队列是否持久化.false:队列在内存中,服务器挂掉后,队列就没了;true:服务器重启后,队列将会重新生成.注意:只是队列持久化,不代表队列中的消息持久化!!!!
         *                         exclusive: false, //队列是否专属,专属的范围针对的是连接,也就是说,一个连接下面的多个信道是可见的.对于其他连接是不可见的.连接断开后,该队列会被删除.注意,不是信道断开,是连接断开.并且,就算设置成了持久化,也会删除.
         *                         autoDelete: true, //如果所有消费者都断开连接了,是否自动删除.如果还没有消费者从该队列获取过消息或者监听该队列,那么该队列不会删除.只有在有消费者从该队列获取过消息后,该队列才有可能自动删除(当所有消费者都断开连接,不管消息是否获取完)
         *                         arguments: null //队列的配置
         *                     );
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
        // TODO 表示开启交换器确认
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean multipart) throws IOException {
                // TODO 表示消息发送交换器成功,第二个参数表示是否批量
                System.out.println("success" + l + multipart);
            }

            @Override
            public void handleNack(long l, boolean multipart) throws IOException {
                // TODO 表示消息发送交换器错误
                System.out.println("fail" + l + multipart);
            }
        });
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int i, String s, String s1, String s2, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                // TODO 没有成功发送到交换器
                // System.out.println(new String(bytes));
            }
        });
        for (int i = 0; i < 50;i ++) {
            channel.basicPublish("directExchangeTest", "directQueueTTL111",true, MessageProperties.PERSISTENT_TEXT_PLAIN, (message).getBytes());
        }

        TimeUnit.SECONDS.sleep(100000);
        channel.close();
        connection.close();
    }
}
