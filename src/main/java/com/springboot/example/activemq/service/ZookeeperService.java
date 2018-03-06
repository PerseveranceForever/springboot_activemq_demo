package com.springboot.example.activemq.service;

import com.springboot.example.activemq.model.Version;

/**
 * @Author zhouguanya
 * @Date 2018/3/5
 * @Description
 */
public interface ZookeeperService {
    /**
     * 保存节点
     * @param version
     */
    void save(Version version);

    /**
     * 更新节点
     * @param version
     */
    void update(Version version);

    /**
     * 删除节点
     * @param version
     */
    void delete(Version version);
}
