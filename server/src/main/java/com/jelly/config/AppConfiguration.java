package com.jelly.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfiguration {
    @Value("${zk.root}")
    private String zkRoot;
    @Value("${zk.addr}")
    private String zkAddr;
    @Value("${zk.switch}")
    private boolean zkSwitch;
    @Value("${zk.connect.timeout}")
    private int zkConnectTimeout;
    @Value("${app.server.port}")
    private int easyIMServerPort;
    @Value("${app.clear.route.request.url")
    private String clearRouterUrl;
    @Value("${app.heartbeat.time}")
    private long heartBeatTime;

    public String getZkRoot() {
        return zkRoot;
    }

    public String getZkAddr() {
        return zkAddr;
    }

    public boolean isZkSwitch() {
        return zkSwitch;
    }

    public int getEasyIMServerPort() {
        return easyIMServerPort;
    }

    public String getClearRouterUrl() {
        return clearRouterUrl;
    }

    public long getHeartBeatTime() {
        return heartBeatTime;
    }

    public int getZkConnectTimeout() {
        return zkConnectTimeout;
    }
}
