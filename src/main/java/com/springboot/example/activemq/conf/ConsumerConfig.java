package com.springboot.example.activemq.conf;

import com.springboot.example.activemq.model.Version;
import com.springboot.example.activemq.service.DestinationService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageListener;

/**
 * @Author zhouguanya
 * @Date 2018/3/7
 * @Description
 */
@Configuration
public class ConsumerConfig {

    final String destinationName = "my_test_mq_destination";

    @Resource(name = "destinationService")
    private DestinationService destinationService;

//    @Bean("defaultMessageListenerContainer")
//    public DefaultMessageListenerContainer defaultMessageListenerContainer (ConnectionFactory connectionFactory, Destination destination, MessageListener messageListener) {
//        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
//        defaultMessageListenerContainer.setConnectionFactory(connectionFactory);
//        defaultMessageListenerContainer.setDestination(destination);
//        defaultMessageListenerContainer.setMessageListener(messageListener);
//        return defaultMessageListenerContainer;
//    }

    @Bean
    public Destination queueDestination() {
        Version version = destinationService.findDestinationVersion(destinationName);
        if (version == null) {
           return new ActiveMQQueue(destinationName + "_1");
        }
        return new ActiveMQQueue(version.uniformDestiantionName());
    }

}
