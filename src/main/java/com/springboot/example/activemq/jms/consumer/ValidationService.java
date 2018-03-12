package com.springboot.example.activemq.jms.consumer;

import javax.jms.Message;

/**
 * @Author zhouguanya
 * @Date 2018/3/12
 * @Description
 */
public interface ValidationService {

    /**
     * 验证消息的版本号
     * @param message
     * @return
     */
    boolean validateVersion(Message message);
}
