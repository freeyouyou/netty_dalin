package com.alison.nettyDemo;


import com.alison.nettyDemo.telnet.NettyTelnetServer;
import org.junit.Test;

public class NettyTest {
    @Test
    public void test(){
        NettyTelnetServer nettyTelnetServer = new NettyTelnetServer();
        try {
            nettyTelnetServer.open();
        } catch (InterruptedException e) {
            nettyTelnetServer.close();
        }
    }
}
