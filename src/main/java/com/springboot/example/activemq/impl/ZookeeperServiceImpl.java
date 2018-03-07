package com.springboot.example.activemq.impl;

import com.alibaba.fastjson.JSON;
import com.springboot.example.activemq.model.Version;
import com.springboot.example.activemq.service.ZookeeperService;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.*;

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
        CuratorFramework curator = initCurator();
        curator.start();
        try {
            curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).
                    forPath(version.getPath(), JSON.toJSONString(version).getBytes());
        } catch (Exception e) {
            logger.error("创建节点失败! 失败节点信息:{}, 异常信息:{}", version, e);
        } finally {
            curator.close();
        }
    }

    @Override
    public void update(Version version) {
        String path = version.getPath();
        CuratorFramework curator = initCurator();
        curator.start();
        try {
            //获取节点数据
            Version zkVersion = find(path);
            TreeCache treeCache = new TreeCache(curator, path);
            treeCache.start();
            ExecutorService executor = newCachedThreadPool();
            treeCache.getListenable().addListener(treeCacheListener, executor);
            AtomicInteger number = zkVersion.getNumber();
            number.getAndIncrement();
            curator.setData().forPath(path, JSON.toJSONBytes(zkVersion));
        } catch (Exception e) {
            logger.error("创建节点失败! 失败节点信息:{}, 异常信息:{}", version, e);
            throw new RuntimeException(e);
        } finally {
            curator.close();
        }
    }

    @Override
    public void delete(Version version) {

    }

    @Override
    public Version find(String path) {
        CuratorFramework curator = initCurator();
        curator.start();
        try {
            //获取节点数据
            String data = new String(curator.getData().forPath(path));
            Version version = JSON.parseObject(data, Version.class);
            return version;
        } catch (Exception e) {
            logger.error("查询节点失败! 失败节点信息:{}, 异常信息:{}", path, e);
        } finally {
            curator.close();
        }
        return null;
    }
}
