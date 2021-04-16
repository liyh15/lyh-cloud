package wibo.cloud.custom.tomcat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Classname ServerConnect
 * @Description TODO
 * @Date 2020/10/10 15:06
 * @Created by lyh
 */
public class ServerConnect {
    private static final int BUF_SIZE=1024;
    private static final int PORT = 8080;
    private static final int TIMEOUT = 3000;
    private static Selector serverSelector = null;
    private static Selector clientSelectoe = null;

    public static void main(String[] args) throws IOException {
         selector();
    }

    public static void doRead(SelectionKey selectionKey) throws IOException {
       if (selectionKey.isReadable()) {
           SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
           ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
           long a = -1;
           while ((a = socketChannel.read(byteBuffer)) > 0) {
               byteBuffer.flip();
               StringBuilder builder = new StringBuilder();
               while (byteBuffer.hasRemaining()) {
                   builder.append((char)byteBuffer.get());
               }
               byteBuffer.clear();
               System.out.println(builder.toString());
           }
           if (a == -1) {
               socketChannel.close();
           }
       }
    }

    public static void doAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(clientSelectoe, SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
    }

    public static void selector() throws IOException {
        serverSelector = Selector.open();
        clientSelectoe = Selector.open();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        if (clientSelectoe.selectNow() == 0) {
                            continue;
                        }
                        Iterator<SelectionKey> selectionKeyIterator = clientSelectoe.selectedKeys().iterator();
                        while (selectionKeyIterator.hasNext()) {
                            SelectionKey selectionKey = selectionKeyIterator.next();
                            if (selectionKey.isReadable()) {
                                doRead(selectionKey);
                            }
                            selectionKeyIterator.remove();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(PORT));
        ssc.configureBlocking(false);
        ssc.register(serverSelector, SelectionKey.OP_ACCEPT);
        while (true) {
            if (serverSelector.selectNow() == 0) {
               continue;
            }
            Iterator<SelectionKey> selectionKeys = serverSelector.selectedKeys().iterator();
            while (selectionKeys.hasNext()) {
                SelectionKey selectionKey = selectionKeys.next();
                if (selectionKey.isAcceptable()) {
                    doAccept(selectionKey);
                }
                selectionKeys.remove();
            }
        }
    }
}
