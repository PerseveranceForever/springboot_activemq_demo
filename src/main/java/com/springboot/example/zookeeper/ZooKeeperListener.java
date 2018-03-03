package com.springboot.example.zookeeper;

import org.apache.curator.framework.CuratorFramework;

/**
 * @Author zhouguanya
 * @Date 2018/3/3
 * @Description ZK监听器
 */
public interface ZooKeeperListener {
    /**
     * 监听zk，需要执行的操作
     * @param client
     */
    void executor(CuratorFramework client);
}