package com.springboot.example.activemq.service;

import com.springboot.example.activemq.controller.ProducerController;
import com.springboot.example.activemq.model.Message;
import com.springboot.example.activemq.model.Version;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @Author zhouguanya
 * @Date 2018/3/7
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DestinationServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(DestinationServiceTest.class);

    @Autowired
    ProducerController producerController;

    @Autowired
    DestinationService destinationService;

    @Test
    public void testChangeDestination() throws InterruptedException {
        Message message = new Message();
        message.setUserId(1L);
        message.setUserName("张三");
        message.setContent("这是一条测试消息");
        Version version = new Version("final.test.destination");
        message.setVersion(version);
        producerController.send(message);
        logger.info("发送消息成功！！！！");
        //消费者监听的默认队列：my_test_mq_destination
        destinationService.updateDestinationVersion(version);
        Thread.sleep(20000);
    }
}
