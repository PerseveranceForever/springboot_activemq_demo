package com.springboot.example.activemq.jms.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import javax.jms.*;

/**
 * @Author zhouguanya
 * @Date 2018/3/2
 * @Description
 */
@Component
public class JmsConsumer implements MessageListener{

    private final static Logger logger = LoggerFactory.getLogger(JmsConsumer.class);

    public JmsConsumer () {
        logger.info("JmsConsumer初始化");
    }

    @DependsOn("jmsListenerAspect")

    @Override
    public void onMessage(Message message) {
        logger.info("接收到消息：{}",message);
    }

}
