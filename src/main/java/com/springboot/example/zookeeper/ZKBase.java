package com.springboot.example.zookeeper;

import org.apache.curator.framework.CuratorFramework;

public abstract class ZKBase {
    protected CuratorFramework curatorFramework;
    public ZKBase(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }
    public void init(){
        try {
            call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected abstract void call() throws Exception;
}