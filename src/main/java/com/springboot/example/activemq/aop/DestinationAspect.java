package com.springboot.example.activemq.aop;

import com.springboot.example.activemq.annotation.JmsDestination;
import com.springboot.example.activemq.utils.DefaultUtils;
import org.apache.zookeeper.ZooKeeper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author zhouguanya
 * @Date 2018/3/2
 * @Description Destination切面
 */
@Aspect
@Component
public class DestinationAspect {

    @Value("spring.zk.url")
    private String SPRING_ZK_URL;

    @Value("spring.zk.destinatin.path")
    private String SPRING_ZK_DESTINATIN_PATH;

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.springboot.example.activemq.annotation.JmsDestination)")
    private void pointcut() {

    }

    /**
     * 在方法执行前后
     *
     * @param point
     * @param jmsDestination
     * @return
     */
    @Around(value = "pointcut() && @annotation(jmsDestination)", argNames="point,jmsDestination")
    public Object around(ProceedingJoinPoint point, JmsDestination jmsDestination) {

        System.out.println("++++执行了around方法++++");

        String destination = jmsDestination.value();
        //TODO 修改destination，模拟从zk注册中心获取到下发的destination信息，后续可以在这里发起请求注册中心，申请版本号
        //TODO 是不是每次都要发起查询注册中心？会不会影响性能？要不要做缓存？注册中心更新的时候刷新缓存？
        destination = destination + "_version";

        //拦截的类名
        Class clazz = point.getTarget().getClass();
        //拦截的方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();

        System.out.println("执行了 类:" + clazz + " 方法:" + method + " destination:" + destination);

        try {
            //执行程序
            return point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return throwable.getMessage();
        }
    }


    private String customizedDestination(String destination){
        return DefaultUtils.EMPTY_STRING;
    }
}
