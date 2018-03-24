package com.springboot.example.activemq.init;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Author zhouguanya
 * @Date 2018/3/5
 * @Description
 */
public class CuratorFactory {

    private static final RetryPolicy DEFAULT_POLICY = new ExponentialBackoffRetry(1000, 10);

    public static CuratorFramework gerCurator(String connectionUrl, int timeout) {
       return CuratorFrameworkFactory.builder().connectString(connectionUrl)
                .sessionTimeoutMs(timeout).retryPolicy(DEFAULT_POLICY).build();
    }
}
