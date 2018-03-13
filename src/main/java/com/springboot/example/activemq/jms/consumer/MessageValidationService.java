package com.springboot.example.activemq.jms.consumer;

import javax.jms.Message;

/**
 * @Author zhouguanya
 * @Date 2018/3/12
 * @Description
 */
public interface MessageValidationService {

    /**
     * 验证消息的版本号
     * @param message
     * @return
     */
    boolean validateMessageVersion(Message message);

    /**
     * 预留接口，消费者可以选择实现对消息其他方面的验证
     * @param message
     * @return
     */
    boolean validationMessageInfo(Message message);
}
