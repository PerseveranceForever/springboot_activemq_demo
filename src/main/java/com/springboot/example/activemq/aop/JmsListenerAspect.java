package com.springboot.example.activemq.aop;

import com.springboot.example.activemq.model.Version;
import com.springboot.example.activemq.service.DestinationService;
import com.springboot.example.activemq.utils.AnnotationUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author zhouguanya
 * @Date 2018/3/6
 * @Description
 */
@Aspect
@Component("jmsListenerAspect")
public class JmsListenerAspect {

    private static final Logger logger = LoggerFactory.getLogger(JmsListenerAspect.class);

    @Autowired
    private DestinationService destinationService;

    /**
     * 切入点
     */
    @Pointcut(value = "@annotation(org.springframework.jms.annotation.JmsListener)")
    private void pointcut() {

    }

    public JmsListenerAspect () {
        logger.info("JmsListenerAspect初始化");
    }
    /**
     * 在方法执行前后
     *
     * @param point
     * @param
     * @return
     */
    @Around(value = "pointcut() && @annotation(jmsListener)", argNames="point,jmsListener")
    public Object around(ProceedingJoinPoint point, JmsListener jmsListener) throws NoSuchFieldException, IllegalAccessException {

        logger.info(">>>>>执行了JmsListenerAspect.around方法>>>>>");

        String destinationName = jmsListener.destination();

        Version version = destinationService.findDestinationVersion(destinationName);

        //修改destination为：destinationName_version形式
        if (version != null) {
            AnnotationUtils.updateAnnotationValue(jmsListener, version.uniformDestiantionName());
        }

        try {
            //执行程序
            return point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return throwable.getMessage();
        }
    }

    @Before(value = "pointcut() && @annotation(jmsListener)", argNames="point,jmsListener")
    public void before(JoinPoint point, JmsListener jmsListener) throws NoSuchFieldException, IllegalAccessException {

        logger.info(">>>>>执行了JmsListenerAspect.before方法>>>>>");

        String destinationName = jmsListener.destination();

        Version version = destinationService.findDestinationVersion(destinationName);

        //修改destination为：destinationName_version形式
        if (version != null) {
            AnnotationUtils.updateAnnotationValue(jmsListener, version.uniformDestiantionName());
        }

    }
}
