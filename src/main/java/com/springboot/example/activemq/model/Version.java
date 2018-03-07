package com.springboot.example.activemq.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author zhouguanya
 * @Date 2018/3/5
 * @Description
 */
@ToString(of = {"path, number"})
public class Version implements Serializable {

    @Getter
    private String prefixPath = "/destination";

    /**
     * 存储具体版本号的节点名称
     */
    @Getter
    private String name = "version";
    @Getter @Setter
    private Date createDate;
    @Getter @Setter
    private Date updateDate;
    @Getter @Setter
    private String userId;
    @Getter @Setter
    private String userName;
    @Getter @Setter
    private String destinationName;

    public Version () {

    }

    public Version (String destinationName) {
        this.destinationName = destinationName;
        uniformDestinationPath();
    }
    /**
     * version节点全路径
     */
    @Getter
    private String path;

    /**
     * 每个destination默认的版本号
     */
    @Getter
    private AtomicInteger number = new AtomicInteger(1);

    /**
     * version节点路径
     * @return
     */
    private void uniformDestinationPath() {
        path = new StringBuffer(prefixPath).append("/").append(destinationName).append("/").append(name).toString();
    }

    /**
     * 更新number值
     */
    public String uniformDestiantionName() {
        return new StringBuffer(destinationName).append("_").append(number.intValue()).toString();
    }
}
