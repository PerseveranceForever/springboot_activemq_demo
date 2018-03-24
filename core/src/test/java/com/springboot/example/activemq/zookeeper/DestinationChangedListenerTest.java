package com.springboot.example.activemq.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author zhouguanya
 * @Date 2018/3/4
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DestinationChangedListenerTest {
    private static final String CONNECT_ADDR = "127.0.0.1:2181";
    private static final int SESSION_TIMEOUT = 5000;
    @Autowired
    private TreeCacheListener treeCacheListener;
    @Test
    public void test() throws Exception {
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 10);
        CuratorFramework curator = CuratorFrameworkFactory.builder().connectString(CONNECT_ADDR).sessionTimeoutMs(SESSION_TIMEOUT)
                .retryPolicy(policy).build();
        curator.start();
        TreeCache treeCache = new TreeCache(curator, "/treeCache");
        treeCache.start();
        ExecutorService executor = Executors.newCachedThreadPool();
        treeCache.getListenable().addListener(treeCacheListener, executor);

        curator.create().forPath("/treeCache", "123".getBytes());
//        Thread.sleep(1000);
        curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/treeCache/c1", "456".getBytes());
//        Thread.sleep(1000);
        curator.setData().forPath("/treeCache", "789".getBytes());
//        Thread.sleep(1000);
        curator.setData().forPath("/treeCache/c1", "910".getBytes());
//        Thread.sleep(1000);
        curator.delete().forPath("/treeCache/c1");
//        Thread.sleep(1000);
        curator.delete().forPath("/treeCache");
//        Thread.sleep(1000);
        curator.close();
    }
}
