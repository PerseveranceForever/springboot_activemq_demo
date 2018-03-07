package com.springboot.example.activemq;

import com.springboot.example.activemq.service.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;

/**
 * @Author zhouguanya
 * @Date 2018/3/2
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JmsTest {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private Producer producer;

    @Test
    public void testJms() throws JMSException {

        for (int i=0;i<10;i++) {
            producer.sendMessage("my_test_mq_destination","hello,world!" + i);
        }

    }
}
