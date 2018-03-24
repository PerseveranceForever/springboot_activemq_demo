package com.springboot.example.activemq.jms.producer;

import com.springboot.example.activemq.model.Message;
import com.springboot.example.activemq.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * @Author zhouguanya
 * @Date 2018/3/2
 * @Description 生产者
 */
@Component
public class JmsProducer implements Producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMessage(Destination destination, Message message) {
        this.jmsTemplate.convertAndSend(destination, message);
    }
}
