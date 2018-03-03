package com.springboot.example.zookeeper.impl;

import com.springboot.example.zookeeper.ZooKeeperListener;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackLevelListener implements ZooKeeperListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String path;

    //Logback日志级别ZNode
    public LogbackLevelListener(String path) {
        this.path = path;
    }

    @Override
    public void executor(CuratorFramework client) {


    }
}