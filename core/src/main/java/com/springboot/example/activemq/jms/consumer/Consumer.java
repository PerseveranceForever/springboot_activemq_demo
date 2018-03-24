package com.springboot.example.activemq.jms.consumer;

import javax.jms.Message;

/**
 * @Author zhouguanya
 * @Date 2018/3/13
 * @Description
 */
public interface Consumer {
    /**
     * 消费者客户端实现此接口
     * @return
     */
    Object listenAndExecute(Message message);
}
