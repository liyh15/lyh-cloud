package wibo.cloud.custom.jvm;

import org.apache.tomcat.util.net.Acceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelector;
import java.util.Iterator;

public class ServerConnect
{
    private static final int BUF_SIZE=1024;
    private static final int PORT = 8088;
    private static final int TIMEOUT = 3000;
    public static void main(String[] args)
    {
        selector();
    }
    public static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel ssChannel = (ServerSocketChannel)key.channel();
        SocketChannel sc = ssChannel.accept();
        sc.configureBlocking(false);
        sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE)); // TODO 创建一个直接内存存储
    }
    public static void handleRead(SelectionKey key) throws IOException{
        SocketChannel sc = (SocketChannel)key.channel();
        ByteBuffer buf = (ByteBuffer)key.attachment();
        long bytesRead = sc.read(buf); // TODO 这里会从内核直接读取到直接内存中，少了一步复制操作
        while(bytesRead>0){
            buf.flip();
            while(buf.hasRemaining()){
                System.out.print((char)buf.get());
            }
            System.out.println();
            buf.clear();
            bytesRead = sc.read(buf);
        }
        key.cancel(); // TODO 在关闭通道前要将key给cancel了
        sc.close();
    }
    public static void handleWrite(SelectionKey key) throws IOException{
        ByteBuffer buf = (ByteBuffer)key.attachment();
        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        while(buf.hasRemaining()){
            sc.write(buf);
        }
        buf.compact();
    }
    public static void selector() {
        Selector selector = null;
        ServerSocketChannel ssc = null;
        try{
            selector = Selector.open();
            ssc= ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(PORT));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT); // 将服务端通道主要测selector上
            while(true){
                /**
                 * .select() 此方法会一直阻塞直到有事件就绪
                 * .select(TIMEOUT) 此方法配置了阻塞时间，超时如果没有事件就绪就返回0
                 * .selectNow() 如果没有事件就绪立即返回0
                 */
                if(selector.selectNow() == 0){
                    continue;
                }
                System.out.println("has connect");
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                // TODO Acceptor
                while(iter.hasNext()){
                    System.out.println("");
                    SelectionKey key = iter.next();
                    // TODO 判断Channel属于哪一种类型，获取，读取，写入，连接
                    System.out.println(key.isValid());
                    System.out.println(key.channel());
                    if (!key.isValid()) {
                        iter.remove();
                        continue;
                    }
                    if(key.isAcceptable()){
                        handleAccept(key);
                    } else if(key.isReadable()){
                        handleRead(key);
                    } else if(key.isWritable()){
                        handleWrite(key);
                    } else if(key.isConnectable()){
                        System.out.println("isConnectable = true");
                    }
                    iter.remove();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(selector!=null){
                    selector.close();
                }
                if(ssc!=null){
                    ssc.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
