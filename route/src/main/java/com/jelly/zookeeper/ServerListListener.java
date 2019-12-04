package com.jelly.zookeeper;

import com.jelly.config.AppConfiguration;
import com.jelly.util.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListListener implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListListener.class);

    private ZKUtil zkUtil;

    private AppConfiguration appConfiguration;

    public ServerListListener() {
        zkUtil = SpringBeanFactory.getBean(ZKUtil.class);
        appConfiguration = SpringBeanFactory.getBean(AppConfiguration.class);
    }

    @Override
    public void run() {
    }
}
