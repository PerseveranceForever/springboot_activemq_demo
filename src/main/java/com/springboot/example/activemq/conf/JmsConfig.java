package com.springboot.example.activemq.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;

/**
 * @Author zhouguanya
 * @Date 2018/3/7
 * @Description
 */
@Configuration
public class JmsConfig {
    @Resource(name = "jsonMessageConverter")
    MessageConverter messageConverter;

    @Bean
    public JmsTemplate getJmsTemplate(ConnectionFactory connectionFactor) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setMessageConverter(messageConverter);
        jmsTemplate.setConnectionFactory(connectionFactor);
        return jmsTemplate;
    }
}
