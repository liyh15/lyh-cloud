package wibo.cloud.security.config;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;

import java.net.URI;
import java.net.URISyntaxException;


public class MyWebSocketClient extends WebSocketClient{

    public MyWebSocketClient(URI serverUri) {
       super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

    }

    @Override
    public void onMessage(String s) {
        System.out.println(s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        MyWebSocketClient client = new MyWebSocketClient(new URI("http://localhost:8090/websocket/1"));
        client.connect();
        while (!client.getReadyState().equals(READYSTATE.OPEN)) {
            System.out.println("连接中···请稍后");
        }
        client.send("123123123123123");
        client.close();
    }
}