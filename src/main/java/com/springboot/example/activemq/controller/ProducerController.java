package com.springboot.example.activemq.controller;

import com.alibaba.fastjson.JSON;
import com.springboot.example.activemq.model.Message;
import com.springboot.example.activemq.model.Version;
import com.springboot.example.activemq.service.Producer;
import com.springboot.example.activemq.service.ZookeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhouguanya
 * @Date 2018/3/7
 * @Description
 */
@RestController
public class ProducerController {
    @Autowired
    private Producer producer;

    @Autowired
    private ZookeeperService zookeeperService;

    /**
     * 发送消息
     * @param message
     */
    @RequestMapping("/message/produce")
    public void send(@RequestBody Message message) {
        String destinationName = message.getVersion().getDestinationName();
        producer.sendMessage(destinationName, JSON.toJSONString(message));
    }

    /**
     *
     * @param version
     */
    @RequestMapping("/message/clear")
    public void clear(@RequestBody Version version) {
        zookeeperService.update(version);
    }
}
