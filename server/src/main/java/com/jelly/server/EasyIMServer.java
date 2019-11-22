package com.jelly.server;

import com.jelly.init.EasyIMServerInitializer;
import com.jelly.vo.req.SendMsgReqVO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class EasyIMServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(EasyIMServer.class);

    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup work = new NioEventLoopGroup();

    @Value("easyIM.server.port")
    private int nettyPort;

    public void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss,work)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(nettyPort))
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new EasyIMServerInitializer());

        ChannelFuture channelFuture = bootstrap.bind().sync();
        if(channelFuture.isSuccess()){
            LOGGER.info("start easyIM server success");
        }
    }

    public void sendMsg(SendMsgReqVO sendMsgReqVO) {
    }
}
