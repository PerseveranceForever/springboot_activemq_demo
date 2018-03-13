package com.springboot.example.activemq.jms.consumer;

import javax.jms.Message;

/**
 * @Author zhouguanya
 * @Date 2018/3/13
 * @Description 默认消费者
 */
public class DefaultConsumer implements Consumer {
    @Override
    public Object listenAndExecute(Message message) {
        return message;
    }
}
