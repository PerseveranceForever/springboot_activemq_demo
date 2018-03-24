package com.springboot.example.activemq.service;

import com.springboot.example.activemq.model.Message;

import javax.jms.Destination;

/**
 * @Author zhouguanya
 * @Date 2018/3/6
 * @Description
 */
public interface Producer {

    /**
     * 发送消息
     * @param destination
     * @param message
     */
    void sendMessage(Destination destination, Message message);

}
