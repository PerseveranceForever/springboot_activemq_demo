package com.springboot.example.activemq.impl;

import com.springboot.example.activemq.model.Version;
import com.springboot.example.activemq.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @Author zhouguanya
 * @Date 2018/3/5
 * @Description
 */
@Service("destinationService")
public class DestinationServiceImpl implements DestinationService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void saveDestinationVersion(Version version) {
        ValueOperations<String, Version> operations = redisTemplate.opsForValue();
        operations.set(version.getDestinationName(), version);
    }

    @Override
    public Version findDestinationVersionNum(String destinationName) {
        ValueOperations<String, Version> operations = redisTemplate.opsForValue();
        return operations.get(destinationName);
    }

    @Override
    public void updateDestinatinVersion(Version version) {

    }
}
