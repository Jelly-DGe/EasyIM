package com.jelly.cache;

import com.google.common.cache.LoadingCache;
import com.jelly.zookeeper.ZKUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ServerCache {

    @Autowired
    private LoadingCache<String, String> cache;

    @Autowired
    private ZKUtil zkUtil;

    private AtomicLong index = new AtomicLong();

    public void addCache(String key) {
        cache.put(key, key);
    }

    /**
     * 更新所有缓存（先删除所有缓存）
     *
     * @param currentChildList
     */
    public void updateCache(List<String> currentChildList) {
        cache.invalidateAll();
        for (String child : currentChildList) {
            String key = child.split("-")[1];
            addCache(key);
        }
    }

    /**
     * 获取所有服务列表
     *
     * @return
     */
    public List<String> getAll() {
        List<String> list = new ArrayList<>();
        if (cache.size() == 0) {
            List<String> allNode = zkUtil.getAllNode();
            for (String node : allNode) {
                String key = node.split("-")[1];
                addCache(key);
            }
        }
        for (Map.Entry<String, String> entry : cache.asMap().entrySet()) {
            list.add(entry.getKey());
        }
        return list;
    }


    /**
     * 顺序选取服务器
     * @return
     */
    public String selectServer() {
        List<String> all = getAll();
        if (all.size() == 0) {
            throw new RuntimeException("EasyIM 可用服务器为0");
        }
        Long position = index.incrementAndGet() % all.size();
        if (position < 0) {
            position = 0L;
        }
        return all.get(position.intValue());
    }
}
