package com.springboot.example.activemq.aop;

import com.springboot.example.activemq.annotation.JmsDestination;
import com.springboot.example.activemq.model.Version;
import com.springboot.example.activemq.service.DestinationService;
import com.springboot.example.activemq.utils.CommonUtils;
import com.springboot.example.activemq.service.ZookeeperService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Topic;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @Author zhouguanya
 * @Date 2018/3/2
 * @Description Destination切面
 */
@Aspect
@Component
public class DestinationAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.zk.url}")
    private String zookeeperUrl;

    @Value("${spring.zk.destination.path}")
    private String destinationPath;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "destinationService")
    private DestinationService destinationService;

    @Resource(name = "zookeeperService")
    private ZookeeperService zookeeperService;
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
    public Object around(ProceedingJoinPoint point, JmsDestination jmsDestination) throws NoSuchFieldException, IllegalAccessException {

        logger.info("++++执行了around方法++++");

        String destinationName = jmsDestination.value();

        //查询注册中心是否已有该destination的版本号，没有需要注册一个版本号，初始值从1开始
        Version version = destinationService.findDestinationVersion(destinationName);
        if (version == null) {
            version = new Version(destinationName);
            //注册version
            zookeeperService.save(version);
            //redis保存version
            destinationService.saveDestinationVersion(version);

            //拦截的类名
            Class clazz = point.getTarget().getClass();
            //拦截的方法
            Method method = ((MethodSignature) point.getSignature()).getMethod();
            Object[] args = point.getArgs();
            for (Object argument : args) {
                if (argument instanceof Topic) {
                    //获取 jmsDestination 这个代理实例所持有的 InvocationHandler
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(jmsDestination);
                    // 获取 AnnotationInvocationHandler 的 memberValues 字段
                    Field field = invocationHandler.getClass().getDeclaredField("memberValues");
                    // 因为这个字段事 private final 修饰，所以要打开权限
                    field.setAccessible(true);
                    // 获取 memberValues
                    Map memberValues = (Map) field.get(invocationHandler);
                    // 修改 value 属性值
                    memberValues.put("value", version.uniformDestiantionName());
                }
            }
            logger.info("{}类，执行了方法{}，destination完整路径：{}", clazz, method, version.getPath());
        }

        try {
            //执行程序
            return point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return throwable.getMessage();
        }
    }


    private String customizedDestination(String destination){
        return CommonUtils.EMPTY_STRING;
    }
}
