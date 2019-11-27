package com.jelly.kit;

import com.jelly.config.AppConfiguration;
import com.jelly.pojo.EasyIMUserInfo;
import com.jelly.util.NettyAttrUtil;
import com.jelly.util.SessionSocketHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ServerHeartBeatHandlerImpl implements HeartBeatHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerHeartBeatHandlerImpl.class);

    @Autowired
    private AppConfiguration appConfiguration;

    @Autowired
    private RouteHandler routeHandler;

    @Override
    public void process(ChannelHandlerContext context) throws Exception {
        long hertBeatTime = appConfiguration.getHeartBeatTime() * 1000;
        Long lastReadTime = NettyAttrUtil.getReaderTime(context.channel());
        long now = System.currentTimeMillis();
        if (lastReadTime != null && now - lastReadTime > hertBeatTime) {
            EasyIMUserInfo userInfo = SessionSocketHolder.getUserId((NioSocketChannel) context.channel());
            if (userInfo != null) {
                LOGGER.info("客户端[{}]心跳超时[{}]ms，需要关闭连接!", userInfo.getUserName(), now - lastReadTime);
            }
            routeHandler.userOffLine(userInfo,context.channel());
            context.channel().close();
        }
    }
}
