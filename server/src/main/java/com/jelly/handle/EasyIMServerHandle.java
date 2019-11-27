package com.jelly.handle;

import com.jelly.kit.HeartBeatHandler;
import com.jelly.pojo.EasyIMUserInfo;
import com.jelly.protocol.EasyIMRequestProto;
import com.jelly.util.SessionSocketHolder;
import com.jelly.util.SpringBeanFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import okhttp3.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class EasyIMServerHandle extends SimpleChannelInboundHandler<EasyIMRequestProto.EasyIMReqProtocol> {

    private final static Logger LOGGER = LoggerFactory.getLogger(EasyIMServerHandle.class);

    private final MediaType mediaType = MediaType.parse("application/json");

    public void channelInactive(ChannelHandlerContext channelHandlerContext) {
        EasyIMUserInfo userInfo = SessionSocketHolder.getUserId((NioSocketChannel) channelHandlerContext.channel());
        if (userInfo != null) {
            LOGGER.warn("[{}]触发 channelInactive 掉线!", userInfo.getUserName());
            userOffLine(userInfo, (NioSocketChannel) channelHandlerContext.channel());
            channelHandlerContext.channel().close();
        }
    }

    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) {
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) obj;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                LOGGER.info("定时检测客户端端是否存活");
                HeartBeatHandler heartBeatHandler = SpringBeanFactory.getBean(ServerhE)
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, EasyIMRequestProto.EasyIMReqProtocol easyIMReqProtocol) throws Exception {

    }
}
