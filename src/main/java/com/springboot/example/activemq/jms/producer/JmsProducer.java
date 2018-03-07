package com.springboot.example.activemq.jms.producer;

import com.springboot.example.activemq.annotation.JmsDestination;
import com.springboot.example.activemq.model.Version;
import com.springboot.example.activemq.service.DestinationService;
import com.springboot.example.activemq.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author zhouguanya
 * @Date 2018/3/2
 * @Description
 */
@Component
public class JmsProducer implements Producer {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private DestinationService destinationService;
    @Override
    public void sendMessage(String destinationName, String message) {
        //初始化Version，redis已存在直接返回，否则注册新的Version
        Version version = destinationService.initVersion(destinationName);
        this.jmsTemplate.convertAndSend(version.uniformDestiantionName(),message);
    }
}
