package com.jelly.handle;

import com.alibaba.fastjson.JSONObject;
import com.jelly.config.AppConfiguration;
import com.jelly.constant.Constants;
import com.jelly.kit.HeartBeatHandler;
import com.jelly.kit.ServerHeartBeatHandlerImpl;
import com.jelly.pojo.EasyIMUserInfo;
import com.jelly.protocol.EasyIMRequestProto;
import com.jelly.util.NettyAttrUtil;
import com.jelly.util.SessionSocketHolder;
import com.jelly.util.SpringBeanFactory;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@ChannelHandler.Sharable
public class EasyIMServerHandle extends SimpleChannelInboundHandler<EasyIMRequestProto.EasyIMReqProtocol> {

    private final static Logger LOGGER = LoggerFactory.getLogger(EasyIMServerHandle.class);

    private final MediaType mediaType = MediaType.parse("application/json");

    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws IOException {
        EasyIMUserInfo userInfo = SessionSocketHolder.getUserId((NioSocketChannel) channelHandlerContext.channel());
        if (userInfo != null) {
            LOGGER.warn("[{}]触发 channelInactive 掉线!", userInfo.getUserName());
            userOffLine(userInfo, (NioSocketChannel) channelHandlerContext.channel());
            channelHandlerContext.channel().close();
        }
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) obj;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                LOGGER.info("定时检测客户端端是否存活");
                HeartBeatHandler heartBeatHandler = SpringBeanFactory.getBean(ServerHeartBeatHandlerImpl.class);
                heartBeatHandler.process(ctx);
            }
        }
        super.userEventTriggered(ctx, obj);
    }

    /**
     * 用户下线
     *
     * @param userInfo
     * @param channel
     */
    private void userOffLine(EasyIMUserInfo userInfo, NioSocketChannel channel) throws IOException {
        LOGGER.info("用户[{}]下线", userInfo.getUserName());
        SessionSocketHolder.remove(channel);
        SessionSocketHolder.removeSession(userInfo.getUserId());
        //清除路由信息
        clearRouteInfo(userInfo);
    }

    private void clearRouteInfo(EasyIMUserInfo userInfo) throws IOException {
        OkHttpClient okHttpClient = SpringBeanFactory.getBean(OkHttpClient.class);
        AppConfiguration appConfiguration = SpringBeanFactory.getBean(AppConfiguration.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userInfo.getUserId());
        jsonObject.put("msg", "offLine");
        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

        Request request = new Request.Builder()
                .url(appConfiguration.getClearRouterUrl())
                .post(requestBody)
                .build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        } finally {
            response.body().close();
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EasyIMRequestProto.EasyIMReqProtocol msg) throws Exception {
        LOGGER.info("收到msg={}", msg.toString());

        //登录 保存状态
        if (msg.getType() == Constants.CommandType.LOGIN) {
            SessionSocketHolder.put(msg.getRequestId(), (NioSocketChannel) ctx.channel());
            SessionSocketHolder.saveSession(msg.getRequestId(), msg.getReqMsg());
            LOGGER.info("客户端[{}]上线成功", msg.getReqMsg());
        }

        //心跳
        if (msg.getType() == Constants.CommandType.PING) {
            NettyAttrUtil.updateReaderTime(ctx.channel(), System.currentTimeMillis());
            //响应客户端消息
            EasyIMRequestProto.EasyIMReqProtocol heartBeat = SpringBeanFactory.getBean("heartBean",
                    EasyIMRequestProto.EasyIMReqProtocol.class);
            ctx.writeAndFlush(heartBeat).addListeners((ChannelFutureListener) future -> {
                if (!future.isSuccess()) {
                    LOGGER.error("IO error,close Channel");
                    future.channel().close();
                }
            });
        }
    }
}
