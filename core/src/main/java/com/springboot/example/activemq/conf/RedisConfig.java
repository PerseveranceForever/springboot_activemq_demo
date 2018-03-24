package com.springboot.example.activemq.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author zhouguanya
 * @Date 2018/3/5
 * @Description
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String,Object>();
        redisTemplate.setConnectionFactory(factory);
        // key序列化方式;（不然会出现乱码;）,但是如果方法上有Long等非String类型的话，会报类型转换错误；
        // 所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现ObjectRedisSerializer
        // 或者JdkSerializationRedisSerializer序列化方式;
        RedisSerializer<String> keyRedisSerializer = new StringRedisSerializer();
        RedisSerializer<Object> valueRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(keyRedisSerializer);
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(valueRedisSerializer);
        redisTemplate.setHashKeySerializer(keyRedisSerializer);
        redisTemplate.setHashValueSerializer(valueRedisSerializer);
        return redisTemplate;
    }

}