package com.lixy.boothigh.service;

import com.lixy.boothigh.excep.ServiceException;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/9/13.
 * @desc redis服务接口
 */
public interface RedisService {
    /**
     * 获取对应key对应值
     * @param key
     * @return
     * @throws Exception
     */
    String getValueByKeyStr(String key)throws ServiceException;

    /**
     * 获取对应key对应值
     * @param key
     * @return
     * @throws Exception
     */
    Object getValueByKeyObj(String key)throws ServiceException;

    /**
     * 设置key,value
     * @param key
     * @param value
     * @throws Exception
     */
    void setKeyToValueStr(String key, String value, long expireTime, TimeUnit unit)throws ServiceException;

    /**
     * 设置key,value
     * @param key
     * @param value
     * @throws Exception
     */
    void setKeyToValueObj(String key, Object value)throws ServiceException;

    /**
     * 布尔值value
     * @param key
     * @param f
     * @throws ServiceException
     */
    void setKeyToValue(String key, Boolean f)throws ServiceException;
    /**
     * 布尔值value 设置超时时间
     * @param key
     * @param f
     * @throws ServiceException
     */
    void setKeyToValueTimeout(String key, Boolean f, long expireTime)throws ServiceException;
    /**
     * 布尔值value 设置超时时间
     * @param key
     * @param f
     * @param unit 时间类型
     * @throws ServiceException
     */
    void setKeyToValueTimeout(String key, Boolean f, long expireTime, TimeUnit unit)throws ServiceException;
    /**
     * value 设置超时时间
     * @param key
     * @param count
     * @param unit 时间类型
     * @throws ServiceException
     */
    void setKeyToValueCount(String key, Integer count, long expireTime, TimeUnit unit)throws ServiceException;
    /**
     * value 设置超时时间
     * @param key
     * @param count
     * @throws ServiceException
     */
    void setKeyToValueCount(String key, Integer count)throws ServiceException;
    /**
     * 根据key获取对应的value
     * @param key
     * @return
     * @throws ServiceException
     */
    Boolean getValueByKey(String key)throws ServiceException;
    /**
     * 根据key获取对应的value
     * @param key
     * @return
     * @throws ServiceException
     */
    Integer getValueByKeyInt(String key)throws ServiceException;

    /**
     * 设置指定mapKey的hashmap的hashKey,value
     * @param mapKey
     * @param hashKey
     * @param value
     * @param expireTime 设置过期时间
     * @return
     * @throws ServiceException
     */
    void setValueByHashKey(String mapKey, String hashKey, Integer value, long expireTime, TimeUnit unit)throws ServiceException;

    /**
     * 设置指定mapKey的hashmap的hashKey,value
     * @param mapKey
     * @param hashKey
     * @param value
     * @throws ServiceException
     */
    void setValueByHashKey(String mapKey, String hashKey, Integer value)throws ServiceException;

    /***
     * 根据mapKey和hashKey获取对应的value
     * @param mapKey
     * @param hashKey
     * @return
     * @throws ServiceException
     */
    Integer getValueByHashKey(String mapKey, String hashKey)throws ServiceException;
    /**
     * @desc: 移除对应key的数据
     * @Author: li_shuai
     * @Date: 2017/10/10
     * @param key
     * @throws ServiceException
     */
    void removeKey(String key)throws ServiceException;

    /**
     * @desc: 是否包含key
     * @Author: li_shuai
     * @Date: 2017/11/15
     * @param key
     * @return
     * @throws ServiceException
     */
    Boolean hasKey(String key)throws ServiceException;
}
