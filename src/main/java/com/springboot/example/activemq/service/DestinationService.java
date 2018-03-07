package com.springboot.example.activemq.service;

import com.springboot.example.activemq.model.Version;

/**
 * @Author zhouguanya
 * @Date 2018/3/5
 * @Description
 */
public interface DestinationService {
    /**
     * 保存
     * @param version
     */
    void saveDestinationVersion(Version version);

    /**
     * destinationName查询Version
     * @param destinationName
     * @return
     */
    Version findDestinationVersion(String destinationName);

    /**
     * 更新
     * @param version
     */
    void updateDestinationVersion(Version version);

    Version initVersion(String destinationName);
}
