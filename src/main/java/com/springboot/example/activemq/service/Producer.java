package com.springboot.example.activemq.service;

import com.springboot.example.activemq.model.Message;

/**
 * @Author zhouguanya
 * @Date 2018/3/6
 * @Description
 */
public interface Producer {

    /**
     * 发送消息
     * @param destinationName
     * @param message
     */
    void sendMessage(String destinationName, Message message);

}
