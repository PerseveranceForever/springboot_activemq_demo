package com.springboot.example.activemq.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

/**
 * @Author zhouguanya
 * @Date 2018/3/7
 * @Description
 */
@Data
@ToString(of = {"userId, userName, content, from, to"})
public class Message {

    private Long userId;

    private String userName;

    private Object content;

    private Date createDate;

    private String from;

    private String to;

    private Version version;
}
