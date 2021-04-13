package wibo.cloud.security.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import wibo.cloud.security.controller.TestController;

/**
 * @Classname NettyClient
 * @Description TODO
 * @Date 2021/3/1 14:25
 * @Created by lyh
 */
public class NettyClient {
    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());

        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8091).sync();
            System.out.println("客户端创建成功");
            //发送消息
            TestController testController = new TestController();

            future.channel().writeAndFlush("asdasdasdasdasdad");
            // 等待连接被关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyClient client = new NettyClient();
        client.start();
    }

}
