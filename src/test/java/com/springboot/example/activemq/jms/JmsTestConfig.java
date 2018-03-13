package com.springboot.example.activemq.jms;

import com.springboot.example.activemq.jms.consumer.DefaultMessageListener;
import com.springboot.example.activemq.jms.consumer.Consumer;
import com.springboot.example.activemq.jms.consumer.MessageValidationService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

import javax.jms.*;

/**
 * @Author zhouguanya
 * @Date 2018/3/13
 * @Description 测试生产者消费者模型
 */
@Configuration
public class JmsTestConfig {

    /**
     * 监听消息的主要容器
     * @param consumer
     * @param validationService
     * @return
     */
    @Bean
    public DefaultMessageListener getAbstractMessageListener(Consumer consumer, MessageValidationService validationService) {
        DefaultMessageListener defaultMessageListener = new DefaultMessageListener(consumer, validationService);
        return defaultMessageListener;
    }

    /**
     * 消费者:假设消费者拿到消息后只是打印消息
     * @return
     */
    @Bean
    public Consumer getConsumer() {
        return message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                String content = textMessage.getText();
                System.out.println("消费者接收到消息: " + content);
            } catch (JMSException e) {
                System.out.println(e);
            }
            return textMessage;
        };
    }

    /**
     * 消息校验器
     * @return
     */
    @Bean
    public MessageValidationService getMessageValidationService() {
        return new MessageValidationService() {
            /**
             * 假设对所有消息都不做校验
             * @param message
             * @return
             */
            @Override
            public boolean validateMessageVersion(Message message) {
                return true;
            }
            /**
             * 假设对所有消息都不做校验
             * @param message
             * @return
             */
            @Override
            public boolean validationMessageInfo(Message message) {
                return true;
            }
        };
    }

    /**
     * 消息监听容器
     * @param connectionFactory
     * @param messageListener
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer getSimpleMessageListenerContainer(ConnectionFactory connectionFactory, MessageListener messageListener) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setMessageListener(messageListener);
        //可配置的消息队列
        simpleMessageListenerContainer.setDestination(new ActiveMQQueue("my_test_mq_destination"));
        return simpleMessageListenerContainer;
    }
}
