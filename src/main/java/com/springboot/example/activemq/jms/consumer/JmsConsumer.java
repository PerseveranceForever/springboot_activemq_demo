package com.springboot.example.activemq.jms.consumer;

import org.apache.activemq.util.ByteSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import javax.jms.*;
import javax.jms.Message;

/**
 * @Author zhouguanya
 * @Date 2018/3/2
 * @Description
 */
@Component
public class JmsConsumer implements MessageListener, ValidationService{

    private final static Logger logger = LoggerFactory.getLogger(JmsConsumer.class);

    @JmsListener(destination = "my_test_mq_destination")
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                String text = textMessage.getText();
                //TODO 由于MessageListener.onMessage不带有返回值，所以消费者调用的时候必须自己手动触发版本号校验。框架代码不好处理
                validateVersion(message);
                logger.info("接收到text：{}",text);
            } catch (JMSException e) {
                logger.error("JMSException:{}", e);
            }
        }
        logger.info("接收到消息：{}",message);
    }

    @Override
    public boolean validateVersion(Message message) {
        return false;
    }
}
