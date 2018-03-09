package com.springboot.example.activemq.jms.container;

import com.springboot.example.activemq.model.Version;
import com.springboot.example.activemq.utils.SpringUtils;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;

/**
 * @Author zhouguanya
 * @Date 2018/3/8
 * @Description
 */
@Component("defaultMessageListenerContainer")
public class SimpleDefaultMessageListenerContainer extends DefaultMessageListenerContainer {

    @Autowired
    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        super.setConnectionFactory(connectionFactory);
    }

    @Autowired
    @Override
    public void setDestination(Destination destination) {
        super.setDestination(destination);
    }

    @Resource(name = "jmsConsumer")
    @Override
    public void setMessageListener(Object messageListener) {
        super.setMessageListener(messageListener);
    }

    public void updateDestinationVersion(Version version) {
        Object defaultMessageListenerContainerObj = SpringUtils.getBean("defaultMessageListenerContainer");
        if (defaultMessageListenerContainerObj != null && defaultMessageListenerContainerObj instanceof DefaultMessageListenerContainer) {
            ActiveMQQueue destination = new ActiveMQQueue(version.uniformDestiantionName());
            DefaultMessageListenerContainer defaultMessageListenerContainer = (DefaultMessageListenerContainer) defaultMessageListenerContainerObj;
            defaultMessageListenerContainer.setDestination(destination);
            defaultMessageListenerContainer.shutdown();
            defaultMessageListenerContainer.initialize();
        }
    }
}
