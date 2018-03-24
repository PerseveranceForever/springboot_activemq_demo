package com.springboot.example.activemq.service;

/**
 * @Author zhouguanya
 * @Date 2018/3/17
 * @Description
 */
public interface EhcacheService {
    // 测试失效情况，有效期为5秒
    public String getTimestamp(String param);

    public String getDataFromDB(String key);

    public void removeDataAtDB(String key);

    public String refreshData(String key);

    public boolean isReserved(String userId);

    public void removeUser(String userId);

    public void removeAllUser();
}
