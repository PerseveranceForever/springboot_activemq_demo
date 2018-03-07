package com.springboot.example.activemq.service;

/**
 * @Author zhouguanya
 * @Date 2018/3/6
 * @Description
 */
public interface Producer {
    void sendMessage(String destinationName, String message);
}
