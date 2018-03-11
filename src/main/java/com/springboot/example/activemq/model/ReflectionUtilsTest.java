package com.springboot.example.activemq.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @Author zhouguanya
 * @Date 2018/3/5
 * @Description
 */
@Data
@ToString(of = {"id", "userName", "password"})
public class ReflectionUtilsTest {

    private Long id;

    private String userName;

    private String password;

    private String email;

    private String phone;

    private Date createDate;

    private Date updateDate;

    private List<String> permissionList;
}
