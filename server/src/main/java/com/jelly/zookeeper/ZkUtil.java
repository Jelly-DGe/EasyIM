package com.jelly.zookeeper;

import com.jelly.config.AppConfiguration;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZkUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkUtil.class);

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private AppConfiguration appConfiguration;

    /**
     * 创建根节点
     */
    public void createRootNode() {
        boolean exist = zkClient.exists(appConfiguration.getZkRoot());
        if (exist) {
            return;
        }
        //创建root
        zkClient.createPersistent(appConfiguration.getZkRoot());
    }

    /**
     * @param path 结点路径
     *             创建临时结点
     */
    public void createEphNode(String path) {
        zkClient.createEphemeral(path);
    }
}
