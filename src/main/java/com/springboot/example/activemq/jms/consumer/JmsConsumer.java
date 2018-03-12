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
                logger.info("接收到text：{}",text);
            } catch (JMSException e) {
                logger.error("JMSException:{}", e);
            }
        }
        logger.info("接收到消息：{}",message);
    }

    @Override
    public boolean validateVersion(Message message) {
        org.apache.activemq.command.Message result = (org.apache.activemq.command.Message) message;
        ByteSequence byteSequence = result.getContent();
        String str = new String(byteSequence.getData());

        return false;
    }
}
