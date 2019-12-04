package com.jelly.zookeeper;

import com.jelly.cache.ServerCache;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ZKUtil {

    private static Logger logger = LoggerFactory.getLogger(ZKUtil.class);

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private ServerCache serverCache;

    public void subscribeEvent(String path) {
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                logger.info("清除/更新本地缓存 parentPath=【{}】,currentChilds=【{}】", parentPath,currentChilds.toString());

            }
        });
    }
}
