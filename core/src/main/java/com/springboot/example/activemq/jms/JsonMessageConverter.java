package com.springboot.example.activemq.jms;

import com.alibaba.fastjson.JSON;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @Author zhouguanya
 * @Date 2018/3/12
 * @Description JSON消息转换器
 */
@Component("jsonMessageConverter")
public class JsonMessageConverter implements MessageConverter {
    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        return session.createTextMessage(JSON.toJSONString(object));
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        TextMessage textMessage = (TextMessage) message;
        return JSON.parse(textMessage.getText());
    }
}
