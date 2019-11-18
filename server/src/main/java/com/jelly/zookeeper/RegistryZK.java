package com.jelly.zookeeper;

import com.jelly.config.AppConfiguration;
import com.jelly.util.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistryZK implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryZK.class);
    private ZkUtil zkUtil;
    private AppConfiguration appConfiguration;

    private String zkIp;
    private int easyIMServerPort;
    private int httpPort;

    public RegistryZK(String zkIp, int easyIMServerPort, int httpPort) {
        this.zkIp = zkIp;
        this.easyIMServerPort = easyIMServerPort;
        this.httpPort = httpPort;
        zkUtil = SpringBeanFactory.getBean(ZkUtil.class);
        appConfiguration = SpringBeanFactory.getBean(AppConfiguration.class);
    }

    @Override
    public void run() {
        zkUtil.createRootNode();

        
        if (appConfiguration.isZkSwitch()) {
            String path = appConfiguration.getZkRoot() + "/ip-" + zkIp + ":" + easyIMServerPort + ":" + httpPort;
            zkUtil.createEphNode(path);
            LOGGER.info("register zookeeper success,msg = [{}]", path);
        }
    }
}
