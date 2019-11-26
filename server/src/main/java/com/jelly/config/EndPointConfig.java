package com.jelly.config;

import com.jelly.endpoint.CustomEndPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndPointConfig {

    @Value("${monitor.channel.map.key}")
    private String channelMap;

    public CustomEndPoint buildEndPoint(){
        CustomEndPoint customEndPoint = new CustomEndPoint(channelMap);
        return  customEndPoint;
    }
}
