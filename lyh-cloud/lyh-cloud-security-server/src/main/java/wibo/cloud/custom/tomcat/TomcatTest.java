package wibo.cloud.custom.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.collections.SynchronizedStack;
import org.apache.tomcat.util.net.NioChannel;
import org.apache.tomcat.util.net.NioEndpoint;

/**
 * @Classname TomcatTest
 * @Description TODO
 * @Date 2020/10/10 9:49
 * @Created by lyh
 */
public class TomcatTest {
    public static void main(String[] args) throws LifecycleException {
        /*Tomcat tomcat = new Tomcat();
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(8080);
        tomcat.setConnector(connector);

        tomcat.start();
        tomcat.getServer().await();*/
        SynchronizedStack<NioChannel> nioChannels = new SynchronizedStack(128, 500);
        System.out.println(nioChannels.pop());
    }
}
