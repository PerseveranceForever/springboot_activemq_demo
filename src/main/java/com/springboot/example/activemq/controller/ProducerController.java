package com.springboot.example.activemq.controller;

import com.springboot.example.activemq.model.Message;
import com.springboot.example.activemq.model.Version;
import com.springboot.example.activemq.service.DestinationService;
import com.springboot.example.activemq.service.Producer;
import com.springboot.example.activemq.service.ZookeeperService;
import com.springboot.example.activemq.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;

/**
 * @Author zhouguanya
 * @Date 2018/3/7
 * @Description
 */
@RestController
public class ProducerController {

    private final static Logger logger = LoggerFactory.getLogger(ProducerController.class);

    @Autowired
    private Producer producer;

    @Autowired
    private ZookeeperService zookeeperService;

    @Autowired
    ProducerController producerController;

    @Autowired
    DestinationService destinationService;

    /**
     * 发送消息
     * @param message
     */
    @RequestMapping("/message/produce")
    public void send(@RequestBody Message message) {
        String destinationName = message.getVersion().getDestinationName();
//        producer.sendMessage(destinationName, JSON.toJSONString(message));
    }

    /**
     *
     * @param version
     */
    @RequestMapping("/message/clear")
    public void clear(@RequestBody Version version) {
        zookeeperService.update(version);
    }

    @RequestMapping("/message/update")
    public Destination update() {
        Object defaultMessageListenerContainerObj = SpringUtils.getBean("defaultMessageListenerContainer");
        if (defaultMessageListenerContainerObj != null && defaultMessageListenerContainerObj instanceof DefaultMessageListenerContainer) {
            DefaultMessageListenerContainer defaultMessageListenerContainer = (DefaultMessageListenerContainer) defaultMessageListenerContainerObj;
            return defaultMessageListenerContainer.getDestination();
        }
        return null;
    }

    @RequestMapping("/message/test")
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
//        destinationService.updateDestinationVersion(version);
//        Thread.sleep(20000);
    }
}
