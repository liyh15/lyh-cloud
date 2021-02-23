package wibo.cloud.security.config;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

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
        MyWebSocketClient client = new MyWebSocketClient(new URI("http://localhost:8090/websocket/12"));
        client.send("123123123123123");
        Thread.sleep(10000);
    }
}