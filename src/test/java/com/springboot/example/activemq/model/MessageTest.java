package com.springboot.example.activemq.model;

import org.junit.Test;

import java.util.Date;

/**
 * @Author zhouguanya
 * @Date 2018/3/12
 * @Description
 */
public class MessageTest {
    @Test
    public void test () {
        Message message = new Message();
        message.setUserId(1L);
        message.setUserName("张三");
        message.setContent("消息体");
        message.setSendDate(new Date());
        message.setFrom("业务系统A");
        message.setTo("业务系统B");
        message.setVersion(new Version());
        System.out.println(">>>>message>>>>" + message);
    }
}
