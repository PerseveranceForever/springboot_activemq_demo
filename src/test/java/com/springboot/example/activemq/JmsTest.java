package com.springboot.example.activemq;

import com.springboot.example.activemq.producer.JmsProducer;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.Destination;

/**
 * @Author zhouguanya
 * @Date 2018/3/2
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JmsTest {
    @Autowired
    private JmsProducer jmsProducer;

    @Test
    public void testJms() {
        Destination destination = new ActiveMQQueue("springboot.queue.test");

        for (int i=0;i<10;i++) {
            jmsProducer.sendMessage(destination,"hello,world!" + i);
        }
    }
}
