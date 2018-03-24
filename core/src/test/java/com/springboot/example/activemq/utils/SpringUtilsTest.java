package com.springboot.example.activemq.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author zhouguanya
 * @Date 2018/3/7
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringUtilsTest {

    @Test
    public void test () {
        Object object = SpringUtils.getBean("destinationService");
        System.out.println("destinationService--->" + object);
    }
}
