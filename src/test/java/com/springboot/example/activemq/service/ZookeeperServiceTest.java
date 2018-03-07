package com.springboot.example.activemq.service;

import com.springboot.example.activemq.model.Version;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author zhouguanya
 * @Date 2018/3/7
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperServiceTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ZookeeperService zookeeperService;
    @Test
    public void testSaveAndUpdate() {
        Version version = new Version("ZookeeperServiceTest");
        zookeeperService.save(version);
        logger.info("保存version成功{}", zookeeperService.find(version.getPath()));
        zookeeperService.update(version);
        logger.info("更新version成功{}", zookeeperService.find(version.getPath()));
    }
}
