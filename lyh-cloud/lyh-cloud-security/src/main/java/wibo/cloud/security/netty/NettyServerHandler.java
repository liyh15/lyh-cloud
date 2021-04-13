package wibo.cloud.security.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Classname NettyServerHandler
 * @Description netty服务端处理器
 * @Date 2021/3/1 11:05
 * @Created by lyh
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有一个客户端已经连接");
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器收到消息" + msg);
        ctx.write("asdadadasdasdasdasdasdasdasdasdasdadsasd");
        ctx.flush();
    }

    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
