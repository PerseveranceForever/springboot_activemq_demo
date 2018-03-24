package com.springboot.example.activemq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author zhouguanya
 * @Date 2018/3/17
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EhcacheServiceTest {
    @Autowired
    EhcacheService ehcacheService;

    /**
     * 返回第一次调用getTimestamp方法的时间
     * @throws InterruptedException
     */
    @Test
    public void testGetTimestamp() throws InterruptedException {
        System.out.println("第一次调用：" + ehcacheService.getTimestamp("param"));
        Thread.sleep(2000);
        System.out.println("2秒之后调用：" + ehcacheService.getTimestamp("param"));
        Thread.sleep(4000);
        System.out.println("再过4秒之后调用：" + ehcacheService.getTimestamp("param"));
    }
}
