package com.springboot.example.activemq.producer;

import com.springboot.example.activemq.annotation.JmsDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * @Author zhouguanya
 * @Date 2018/3/2
 * @Description
 */
@Component
public class JmsProducer {
    @Autowired
    private JmsTemplate jmsTemplate;
    @JmsDestination("123")
    public void sendMessage(Destination destination, String message) {
        this.jmsTemplate.convertAndSend(destination,message);
    }
}
