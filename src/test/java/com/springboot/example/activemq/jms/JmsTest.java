package com.springboot.example.activemq.jms;

import com.springboot.example.activemq.jms.consumer.MessageValidationService;
import com.springboot.example.activemq.model.Message;
import com.springboot.example.activemq.model.Version;
import com.springboot.example.activemq.service.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;
import java.util.Date;

/**
 * @Author zhouguanya
 * @Date 2018/3/2
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JmsTest {

    @Autowired
    private Producer producer;
    @Autowired
    MyConsumer myConsumer;

    @Test
    public void testJms() throws JMSException, InterruptedException {

        MessageValidationService validationService = new MyValidateServiceImpl();
        myConsumer.setValidationService(validationService);

        for (int i=0;i<10;i++) {
            Message message = new Message();
            message.setUserId(Long.valueOf(i));
            message.setUserName("张三");
            message.setContent("消息体");
            message.setSendDate(new Date());
            message.setFrom("业务系统A");
            message.setTo("业务系统B");
            message.setVersion(new Version());
            producer.sendMessage("my_test_mq_destination",message);
            //等待消费者消费结束
            Thread.sleep(100);
        }
    }
}
