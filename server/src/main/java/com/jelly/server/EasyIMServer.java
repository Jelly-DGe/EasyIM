package com.jelly.server;

import com.jelly.vo.req.SendMsgReqVO;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EasyIMServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(EasyIMServer.class);

    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup work = new NioEventLoopGroup();

    public void sendMsg(SendMsgReqVO sendMsgReqVO) {
    }
}
