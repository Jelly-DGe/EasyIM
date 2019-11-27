package com.jelly.kit;

import com.jelly.pojo.EasyIMUserInfo;
import io.netty.channel.Channel;
import okhttp3.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(RouteHandler.class);
    private final MediaType mediaType = MediaType.parse("application/json");

    public void userOffLine(EasyIMUserInfo userInfo, Channel channel) {
    }
}
