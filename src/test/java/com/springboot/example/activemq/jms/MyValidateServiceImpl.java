package com.springboot.example.activemq.jms;

import com.springboot.example.activemq.jms.consumer.MessageValidationService;

import javax.jms.Message;

/**
 * @Author zhouguanya
 * @Date 2018/3/13
 * @Description
 */
public class MyValidateServiceImpl implements MessageValidationService {
    @Override
    public boolean validateMessageVersion(Message message) {
        return true;
    }

    @Override
    public boolean validationMessageInfo(Message message) {
        return false;
    }
}
