package com.springboot.example.activemq.jms.consumer;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jms.*;
import javax.jms.Message;

/**
 * @Author zhouguanya
 * @Date 2018/3/2
 * @Description 监听消息
 */
public class DefaultMessageListener implements MessageListener {

    private final static Logger logger = LoggerFactory.getLogger(DefaultMessageListener.class);

    @Setter
    private Consumer consumer;
    @Setter
    private MessageValidationService validationService;

    public DefaultMessageListener() {

    }

    public DefaultMessageListener(Consumer consumer, MessageValidationService validationService) {
        this.consumer = consumer;
        this.validationService = validationService;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                /**
                 * TODO 由于MessageListener.onMessage不带有返回值，所以消费者调用的时候必须自己手动触发版本号校验。框架代码不好处理
                 *
                 * 或者将消费者抽象成接口，由客户自己实现获取数据的方法，在此处onMessage方法获取到数据后，框架主动调用客户端的方法，
                 * 将消息主动设置到客户端的接口中
                 */
                if (consumer == null) {
                    consumer = new DefaultConsumer();
                }
                if (validationService == null) {
                    validationService = new DefaultValidationServiceImpl();
                }
                //至此consumer和validationService都非空。用validationService的方法对消息做校验
                if (validationService.validateMessageVersion(message) && validationService.validationMessageInfo(message)) {
                    consumer.listenAndExecute(textMessage);
                }
            } catch (Exception e) {
                logger.error("AbstractMessageListener监听消息异常: ", e);
            }
        }
    }
}
