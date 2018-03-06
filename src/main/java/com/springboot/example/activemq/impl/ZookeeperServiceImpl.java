package com.springboot.example.activemq.impl;

import com.alibaba.fastjson.JSON;
import com.springboot.example.activemq.model.Version;
import com.springboot.example.activemq.service.ZookeeperService;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author zhouguanya
 * @Date 2018/3/5
 * @Description
 */
@Service("zookeeperService")
public class ZookeeperServiceImpl implements ZookeeperService {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperServiceImpl.class);
    @Value("${spring.zk.url}")
    private String zookeeperUrl;

    @Value("${spring.zk.destination.path}")
    private String destinationPath;
    private static final int SESSION_TIMEOUT = 5000;

    @Autowired
    private TreeCacheListener treeCacheListener;

    private CuratorFramework initCurator() {
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 10);
        return CuratorFrameworkFactory.builder().connectString(zookeeperUrl).sessionTimeoutMs(SESSION_TIMEOUT)
                .retryPolicy(policy).build();
    }

    @Override
    public void save(Version version) {
        try {
            CuratorFramework curator = initCurator();
            curator.start();
            curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).
                    forPath(version.getPath(), JSON.toJSONString(version).getBytes());
            curator.close();
        } catch (Exception e) {
            logger.error("创建节点失败! 失败节点信息:{}, 异常信息:{}", version, e);
        }
    }

    @Override
    public void update(Version version) {

    }

    @Override
    public void delete(Version version) {

    }
}
