package com.springboot.example.zookeeper.init;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author zhouguanya
 * @Date 2018/3/5
 * @Description 查询注册中心已经存在的Destination及其版本号并存储到redis
 */
@Component
public class DestinationInit {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${spring.zk.url}")
    private String zookeeperUrl;

    @Value("${spring.zk.destination.path}")
    private String zookeeperDestinationPath;

    private static final int SESSION_TIMEOUT = 5000;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    private void initDestinations() throws Exception {
        logger.info(">>>>>初始化加载已有destination开始>>>>>");
        //通过工厂创建Curator
        CuratorFramework curator = CuratorFactory.gerCurator(zookeeperUrl, SESSION_TIMEOUT);
        //开启连接
        curator.start();
        //获取/destination子节点，既是所有的已注册的destination
        List<String> destinations = curator.getChildren().forPath(zookeeperDestinationPath);
        logger.info(">>>>>{}下的子节点为{}>>>>>", zookeeperDestinationPath, destinations);
        if (CollectionUtils.isEmpty(destinations)) {
            return;
        }

        //得到每个destination的子节点
        for (String destination : destinations) {
            String destinationPath;
            if (zookeeperDestinationPath.endsWith("/")) {
                destinationPath = new StringBuffer(zookeeperDestinationPath).append(destination).toString();
            } else {
                destinationPath = new StringBuffer(zookeeperDestinationPath).append("/").append(destination).toString();
            }
            List<String> destinationVersions = curator.getChildren().forPath(destinationPath);
            if (CollectionUtils.isEmpty(destinationVersions)) {
                continue;
            }

            //约定每个destination下只有一个版本号节点即：/destination/xxx/version
            String versionPath;
            if (destinationPath.endsWith("/")) {
                versionPath = new StringBuffer(destinationPath).append(destinationVersions.get(0)).toString();
            } else {
                versionPath = new StringBuffer(destinationPath).append("/").append(destinationVersions.get(0)).toString();
            }
            //每个destination对应的版本号
            String version = new String(curator.getData().forPath(versionPath));

            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            // 插入缓存
            operations.set(destination, version);
        }
        curator.close();
        logger.info(">>>>>初始化加载已有destination结束>>>>>");
    }
}
