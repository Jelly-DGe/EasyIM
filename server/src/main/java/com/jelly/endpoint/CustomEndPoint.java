package com.jelly.endpoint;

import com.jelly.util.SessionSocketHolder;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;

import java.util.Map;

public class CustomEndPoint extends AbstractEndpoint<Map<Long, NioSocketChannel>> {

    public CustomEndPoint(String id) {
        //false 表示不是敏感端点
        super(id, false);
    }

    @Override
    public Map<Long, NioSocketChannel> invoke() {
        return SessionSocketHolder.getMAP();
    }
}
