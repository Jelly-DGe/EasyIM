package com.jelly.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfiguration {

    @Value("${zk.root}")
    private String zkRoot;

    @Value("${zk.addr}")
    private String zkAddr;


    @Value("${server.port}")
    private int port;

    @Value("${zk.connect.timeout}")
    private int zkConnectTimeout;

    @Value("${route.way}")
    private String routeWay;

    @Value("${route.way.consitenthash}")
    private String consistentHashWay;

    public String getZkRoot() {
        return zkRoot;
    }

    public String getZkAddr() {
        return zkAddr;
    }

    public int getPort() {
        return port;
    }

    public int getZkConnectTimeout() {
        return zkConnectTimeout;
    }

    public String getRouteWay() {
        return routeWay;
    }

    public String getConsistentHashWay() {
        return consistentHashWay;
    }
}
