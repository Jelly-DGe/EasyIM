package com.jelly.init;

import com.jelly.handle.EasyIMServerHandle;
import com.jelly.protocol.EasyIMRequestProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

public class EasyIMServerInitializer extends ChannelInitializer<Channel> {
    private final EasyIMServerHandle easyIMServerHandle = new EasyIMServerHandle();
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                //11 秒没有向客户端发送消息就发生心跳
                .addLast(new IdleStateHandler(11,0,0))
                // google Protobuf 编解码
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(EasyIMRequestProto.EasyIMReqProtocol.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(easyIMServerHandle);
    }
}
