package com.jelly.init;

import com.jelly.handle.EasyIMServerHandle;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class EasyIMServerInitializer extends ChannelInitializer<Channel> {
    private final EasyIMServerHandle easyIMServerHandle = new EasyIMServerHandle();
    @Override
    protected void initChannel(Channel channel) throws Exception {

    }
}
