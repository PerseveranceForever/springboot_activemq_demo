package com.springboot.example.activemq.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @Author zhouguanya
 * @Date 2018/3/6
 * @Description
 */
public class AnnotationUtils {
    /**
     * 反射修改注解值
     * @param object
     * @param value
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void updateAnnotationValue(Object object, Object value) throws NoSuchFieldException, IllegalAccessException {
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(object);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field field = invocationHandler.getClass().getDeclaredField("memberValues");
        // 因为这个字段事 private final 修饰，所以要打开权限
        field.setAccessible(true);
        // 获取 memberValues
        Map memberValues = (Map) field.get(invocationHandler);
        // 修改 value 属性值
        memberValues.put("value", value);
    }
}
