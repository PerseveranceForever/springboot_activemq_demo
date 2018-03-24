package com.springboot.example.activemq.impl;

import com.springboot.example.activemq.model.Version;
import com.springboot.example.activemq.service.DestinationService;
import com.springboot.example.activemq.service.ZookeeperService;
import com.springboot.example.activemq.utils.SpringUtils;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author zhouguanya
 * @Date 2018/3/5
 * @Description
 */
@Service("destinationService")
public class DestinationServiceImpl implements DestinationService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource(name = "destinationService")
    private DestinationService destinationService;
    @Value("${spring.zk.url}")
    private String zookeeperUrl;

    @Value("${spring.zk.destination.path}")
    private String destinationPath;

    @Resource(name = "zookeeperService")
    private ZookeeperService zookeeperService;

    @Override
    public void saveDestinationVersion(Version version) {
        ValueOperations<String, Version> operations = redisTemplate.opsForValue();
        operations.set(version.getDestinationName(), version);
    }

    @Override
    public Version findDestinationVersion(String destinationName) {
        ValueOperations<String, Version> operations = redisTemplate.opsForValue();
        return operations.get(destinationName);
    }

    /**
     * 修改defaultMessageListenerContainer bean中的Destination
     * @param version
     */
    @Override
    public void updateDestinationVersion(Version version) {
        Object defaultMessageListenerContainerObj = SpringUtils.getBean("defaultMessageListenerContainer");
        if (defaultMessageListenerContainerObj != null && defaultMessageListenerContainerObj instanceof DefaultMessageListenerContainer) {
            ActiveMQQueue destination = new ActiveMQQueue(version.uniformDestiantionName());
            DefaultMessageListenerContainer defaultMessageListenerContainer = (DefaultMessageListenerContainer) defaultMessageListenerContainerObj;
            defaultMessageListenerContainer.setDestination(destination);

        }
    }

    @Override
    public Version initVersion(String destinationName) {
        //查询注册中心是否已有该destination的版本号，没有需要注册一个版本号，初始值从1开始
        Version version = destinationService.findDestinationVersion(destinationName);
        if (version == null) {
            version = new Version(destinationName);
            //注册version
            zookeeperService.save(version);
            //redis保存version
            destinationService.saveDestinationVersion(version);
        }
        return version;
    }
}
