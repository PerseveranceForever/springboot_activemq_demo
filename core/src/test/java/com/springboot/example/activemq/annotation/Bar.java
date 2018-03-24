package com.springboot.example.activemq.annotation;

/**
 * @Author zhouguanya
 * @Date 2018/3/11
 * @Description
 */
public class Bar {
    @Foo("test.annotation.value")
    private String value;
}
