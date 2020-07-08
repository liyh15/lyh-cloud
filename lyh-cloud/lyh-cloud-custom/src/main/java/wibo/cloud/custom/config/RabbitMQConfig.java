package wibo.cloud.custom.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;

@Configuration
@Slf4j
public class RabbitMQConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    /**
     * 新建一个hello队列
     * @return
     */
    @Bean
    public Queue helloQueue() {
        return new Queue("hello",true, false, false);
    }

    /**
     * 新建一个hello队列
     * @return
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
    @Bean
    public Queue userQueue() {
        return new Queue("user");
    }

    /**
     * 新建一个hello队列
     * @return
     */
    @Bean
    public Queue directQueue() {
        return new Queue("direct");
    }

    //=====以下是验证topic Exchange的队列============
    @Bean("message")
    public Queue message() {
        return new Queue("topic.message");
    }

    @Bean("messages")
    public Queue messages() {
        return new Queue("topic.messages");
    }
    //=====以上是验证topic Exchange的队列============
    // =============以下是验证Fanout Exchange的队列
    @Bean("FA")
    public Queue FA() {
        return new Queue("fanout.A");
    }

    @Bean("FB")
    public Queue FB() {
        return new Queue("fanout.B");
    }

    @Bean("FC")
    public Queue FC() {
        return new Queue("fanout.C");
    }
    // =============以上是验证Fanout Exchange的队列
    /**
     *     exchange是交换机交换机的主要作用是接收相应的消息并且绑定到指定的队列.交换机有四种类型,分别为Direct,topic,headers,Fanout.
     *
     * 　　Direct是RabbitMQ默认的交换机模式,也是最简单的模式.即创建消息队列的时候,指定一个BindingKey.当发送者发送消息的时候,指定对应的Key.当Key和消息队列的BindingKey一致的时候,消息将会被发送到该消息队列中.
     *
     * 　　topic转发信息主要是依据通配符,队列和交换机的绑定主要是依据一种模式(通配符+字符串),而当发送消息的时候,只有指定的Key和该模式相匹配的时候,消息才会被发送到该消息队列中.
     *
     * 　　headers也是根据一个规则进行匹配,在消息队列和交换机绑定的时候会指定一组键值对规则,而发送消息的时候也会指定一组键值对规则,当两组键值对规则相匹配的时候,消息会被发送到匹配的消息队列中.
     *
     * 　　Fanout是路由广播的形式,将会把消息发给绑定它的全部队列,即便设置了key,也会被忽略.
     */

    /**
     * direct的exchannge
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
          return new DirectExchange("directExchange");
    }

    /**
     * topic的exchannge
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    /**
     * fanout的exchannge
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    // ===========以下将DiectExchange和queue进行一个组合
    @Bean
    public Binding bindingExchangeDirect(@Qualifier("directQueue") Queue queue, DirectExchange directExchange) {
       return BindingBuilder.bind(queue).to(directExchange).with("direct");
    }

    @Bean
    public Binding bindingExchangeDirectHello(@Qualifier("helloQueue") Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with("hello");
    }


    //===========以下将TopicExchange和queue进行组合
    @Bean
    public Binding bindingExchangeTopicMessage(@Qualifier("message") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("topic.message");
    }

    @Bean
    public Binding bindingExchangeTopicMessages(@Qualifier("messages") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("topic.#");
    }

    // =========以下是fonoutExchange和queue进行组合
    @Bean
    public Binding bindingExchangeA(@Qualifier("FA") Queue AMessage,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    public Binding bindingExchangeB(@Qualifier("FB") Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    public Binding bindingExchangeC(@Qualifier("FC") Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        //若使用confirm-callback或return-callback，必须要配置publisherConfirms或publisherReturns为true
        //每个rabbitTemplate只能有一个confirm-callback和return-callback，如果这里配置了，那么写生产者的时候不能再写confirm-callback和return-callback
        //使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 表示消息发送失败可以走回调接口return-callback
        rabbitTemplate.setMandatory(true);

//        /**
//         * 如果消息没有到exchange,则confirm回调,ack=false
//         * 如果消息到达exchange,则confirm回调,ack=true
//         * exchange到queue成功,则不回调ReturnCallback
//         * exchange到queue失败,则回调ReturnCallback(需设置mandatory=true,否则不会回调,消息就丢了)
//         */
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if(ack){
                    log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
                }else{
                    log.info("消息发送失败:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
                }
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }

    /**
     * 创建互联互通 消息监听
     */
    @Bean
    public SimpleMessageListenerContainer wxDelayMessageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(helloQueue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(10);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                try {
                    //false只确认当前一个消息收到，true确认所有consumer获得的消息
                    log.info("微信获取access_token监听推送消息：{}",new String(message.getBody()));
                    if (true) {
                        log.error("微信获取access_token推送消息失败");
                    }
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (Exception e) {
                    log.info("重新获取微信请求access_token消息 失败：{}，详情：{}", new String(message.getBody()),e);
                }
            }
        });
        return container;
    }
}
