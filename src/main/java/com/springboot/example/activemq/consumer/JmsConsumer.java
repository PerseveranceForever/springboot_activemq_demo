package com.springboot.example.activemq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @Author zhouguanya
 * @Date 2018/3/2
 * @Description
 */
@Component
public class JmsConsumer implements MessageListener {
    private final static Logger logger = LoggerFactory.getLogger(JmsConsumer.class);

    @JmsListener(destination = "springboot.queue.test")
    @Override
    public void onMessage(Message message) {
        logger.info("接收到消息：{}",message);
    }
}
