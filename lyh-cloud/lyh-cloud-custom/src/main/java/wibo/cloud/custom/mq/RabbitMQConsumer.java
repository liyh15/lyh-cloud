package wibo.cloud.custom.mq;

import com.rabbitmq.client.ConnectionFactory;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class RabbitMQConsumer {

    /**
     * 一个队列一个i奥菲这
     */
    public static void SimpleConsumer() {

    }

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("10.11.1.52");
        factory.setPort(15672);
        factory.setUsername("admin");
        factory.setPassword("admin");

    }
}
