package com.springboot.example.activemq.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author zhouguanya
 * @Date 2018/3/2
 * @Description
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JmsDestination {
    String value();
}
