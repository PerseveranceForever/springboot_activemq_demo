package com.springboot.example.activemq.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author zhouguanya
 * @Date 2018/3/7
 * @Description
 */
@Data
public class Message implements Serializable{

    private static final long serialVersionUID = 5938153451560334808L;
    /**
     * 消息id
     */
    private Long id;
    /**
     * 发送消息的用户Id
     */
    private Long userId;
    /**
     * 发送消息的用户名
     */
    private String userName;
    /**
     * 发送消息的内容
     */
    private Object content;
    /**
     * 发送发送实现
     */
    private Date sendDate;
    /**
     * 消息来源
     */
    private String from;
    /**
     * 消息发送地址
     */
    private String to;
    /**
     * 消息版本号
     */
    private Version version;
}
