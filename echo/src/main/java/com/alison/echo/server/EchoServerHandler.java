package com.alison.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Author alison
 * @Date 2019/7/29  23:20
 * @Version 1.0
 * @Description
 */
//1
//    Sharable 标识这类的实例之间可以在 channel 里面共享
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        // 2  日志消息输出到控制台
        System.out.println("server received: " + in.toString(CharsetUtil.UTF_8));
        //3 将所接收的消息返回给发送者。注意，这还没有冲刷数据
        ctx.write(in);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 4 冲刷所有待审消息到远程节点。关闭通道后，操作完成
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 5 打印异常堆栈跟踪
        cause.printStackTrace();
        // 6 关闭通道
        ctx.close();
    }
}

// ChannelHandler 是给不同类型的事件调用
// 应用程序实现或扩展 ChannelHandler 挂接到事件生命周期和提供自定义应用逻辑。
