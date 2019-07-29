package com.alison.nettyDemo.telnet;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.util.Date;

/**
 * 业务处理类
 */
public class NettyTelnetHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // send greeting for a new connection
        ctx.write("welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now. \r\n");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        String response;
        boolean close = false;
        if (request.isEmpty()) {
            response = "please type something. \r\n";
        } else if ("bye".equals(request.toLowerCase())) {
            response = "have a good day!.\r\n";
            close = true;
        } else {
            response = "Did you say '" + request + "' ? \r\n";
        }
        ChannelFuture future = ctx.write(response);
        ctx.flush();
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
