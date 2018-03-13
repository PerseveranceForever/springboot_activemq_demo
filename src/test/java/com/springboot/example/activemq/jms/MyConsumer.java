package com.springboot.example.activemq.jms;

import com.springboot.example.activemq.jms.consumer.AbstractMessageListener;
import com.springboot.example.activemq.jms.consumer.Consumer;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @Author zhouguanya
 * @Date 2018/3/13
 * @Description
 */
@Service
public class MyConsumer extends AbstractMessageListener implements Consumer {
    @Override
    public Object listenAndExecute(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String content = textMessage.getText();
            System.out.println("MyConsumer接收到消息: " + content);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setConsumer(Consumer consumer) {
        super.setConsumer(this);
    }
}
