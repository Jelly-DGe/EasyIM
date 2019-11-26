package com.jelly.server;

import com.jelly.init.EasyIMServerInitializer;
import com.jelly.util.SessionSocketHolder;
import com.jelly.vo.req.SendMsgReqVO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

@Component
public class EasyIMServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(EasyIMServer.class);

    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup work = new NioEventLoopGroup();

    @Value("easyIM.server.port")
    private int nettyPort;

    /**
     * 启动easyIM service
     *
     * @throws InterruptedException
     * @PostConstruct 注入之后执行该方法
     */
    @PostConstruct
    public void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, work)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(nettyPort))
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new EasyIMServerInitializer());

        ChannelFuture channelFuture = bootstrap.bind().sync();
        if (channelFuture.isSuccess()) {
            LOGGER.info("start easyIM server success");
        }
    }

    /**
     * 销毁
     *
     * @PreDestroy 销毁之前执行该方法
     */
    @PreDestroy
    public void destory() {
        boss.shutdownGracefully().syncUninterruptibly();
        work.shutdownGracefully().syncUninterruptibly();
        LOGGER.info("close easyIM server success");
    }

    public void sendMsg(SendMsgReqVO sendMsgReqVO) {
        NioSocketChannel socketChannel = SessionSocketHolder.get(sendMsgReqVO.getUserId());
        if (null == socketChannel) {
            throw new NullPointerException("Client [" + sendMsgReqVO.getUserId() + "] Offline！");
        }

    }
}
