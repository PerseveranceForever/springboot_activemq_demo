package com.springboot.example.zookeeper.impl;

import com.springboot.example.zookeeper.service.ZooKeeperService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author zhouguanya
 * @Date 2018/3/4
 * @Description 监听Destination改变
 */
@Service
public class DestinationChangedListener implements TreeCacheListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
        switch (treeCacheEvent.getType()) {
            case NODE_ADDED:
                System.out.println("NODE_ADDED：路径：" + treeCacheEvent.getData().getPath() + "，数据：" + new String(treeCacheEvent.getData().getData())
                        + "，状态：" + treeCacheEvent.getData().getStat());
                break;
            case NODE_UPDATED:
                System.out.println("NODE_UPDATED：路径：" + treeCacheEvent.getData().getPath() + "，数据：" + new String(treeCacheEvent.getData().getData())
                        + "，状态：" + treeCacheEvent.getData().getStat());
                break;
            case NODE_REMOVED:
                System.out.println("NODE_REMOVED：路径：" + treeCacheEvent.getData().getPath() + "，数据：" + new String(treeCacheEvent.getData().getData())
                        + "，状态：" + treeCacheEvent.getData().getStat());
                break;
            default:
                break;
        }
    }
}