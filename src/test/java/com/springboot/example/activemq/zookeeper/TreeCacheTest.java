package com.springboot.example.activemq.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @Author zhouguanya
 * @Date 2018/3/4
 * @Description
 */
public class TreeCacheTest {
    private static final String CONNECT_ADDR = "127.0.0.1:2181";
    private static final int SESSION_TIMEOUT = 5000;

    public static void main(String[] args) throws Exception {
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 10);
        CuratorFramework curator = CuratorFrameworkFactory.builder().connectString(CONNECT_ADDR).sessionTimeoutMs(SESSION_TIMEOUT)
                .retryPolicy(policy).build();
        curator.start();
        TreeCache treeCache = new TreeCache(curator, "/treeCache");
        treeCache.start();
        treeCache.getListenable().addListener((curatorFramework, treeCacheEvent) -> {
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
        });

        curator.create().forPath("/treeCache", "123".getBytes());
        Thread.sleep(1000);
        curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/treeCache/c1", "456".getBytes());
        Thread.sleep(1000);
        curator.setData().forPath("/treeCache", "789".getBytes());
        Thread.sleep(1000);
        curator.setData().forPath("/treeCache/c1", "910".getBytes());
        Thread.sleep(1000);
        curator.delete().forPath("/treeCache/c1");
        Thread.sleep(1000);
        curator.delete().forPath("/treeCache");
        Thread.sleep(1000);
        curator.close();
    }
}
