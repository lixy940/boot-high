package com.lixy.boothigh.service.impl;

import com.lixy.boothigh.excep.ServiceException;
import com.lixy.boothigh.service.RedisService;
import com.lixy.boothigh.vo.TaskHandleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author:li_shuai
 * @desc:redis服务接口实现
 * @date:Create in 11:51 2017/9/13
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public String getValueByKeyStr(String key) throws ServiceException {
        ValueOperations<String, String> operations=redisTemplate.opsForValue();
        return operations.get(key);
    }

    @Override
    public Object getValueByKeyObj(String key) throws ServiceException {
        ValueOperations<String, Object> operations=redisTemplate.opsForValue();
        return  operations.get(key);
    }

    @Override
    public void setKeyToValueStr(String key, String value,long expireTime,TimeUnit unit) throws ServiceException {
        ValueOperations<String, String> operations=redisTemplate.opsForValue();
        operations.set(key, value,expireTime,unit);
    }

    @Override
    public void setKeyToValueObj(String key, Object value) throws ServiceException {
        ValueOperations<String, Object> operations=redisTemplate.opsForValue();
        operations.set(key, value);
    }

    @Override
    public void setKeyToValue(String key, Boolean f) throws ServiceException {
        ValueOperations<String, Boolean> operations = redisTemplate.opsForValue();
        operations.set(key, f);
    }

    @Override
    public void setKeyToValueTimeout(String key, Boolean f, long expireTime) throws ServiceException {
        ValueOperations<String, Boolean> operations = redisTemplate.opsForValue();
        operations.set(key, f, expireTime, TimeUnit.SECONDS);
    }

    @Override
    public void setKeyToValueTimeout(String key, Boolean f, long expireTime, TimeUnit unit) throws ServiceException {
        ValueOperations<String, Boolean> operations = redisTemplate.opsForValue();
        operations.set(key, f, expireTime, unit);
    }

    @Override
    public void setKeyToValueCount(String key, Integer count, long expireTime, TimeUnit unit) throws ServiceException {
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        operations.set(key, count, expireTime, unit);
    }

    @Override
    public void setKeyToValueCount(String key, Integer count) throws ServiceException {
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        operations.set(key,count);
    }

    @Override
    public Boolean getValueByKey(String key) throws ServiceException {
        ValueOperations<String, Boolean> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    @Override
    public Integer getValueByKeyInt(String key) throws ServiceException {
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    @Override
    public void setValueByHashKey(String mapKey, String hashKey, Integer value, long expireTime, TimeUnit unit) throws ServiceException {
        HashOperations<String,String,Integer> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(mapKey, hashKey, value);
        //设置过期时间
        hashOperations.getOperations().expire(mapKey, expireTime, unit);
    }

    @Override
    public void setValueByHashKey(String mapKey, String key, Integer value) throws ServiceException {
        HashOperations<String,String,Integer> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(mapKey,key,value);
    }

    @Override
    public Integer getValueByHashKey(String mapKey, String hashKey) throws ServiceException {
        HashOperations<String,String,Integer> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(mapKey, hashKey);
    }

    @Override
    public void removeKey(String key) throws ServiceException {
         redisTemplate.delete(key);
    }

    @Override
    public Boolean hasKey(String key) throws ServiceException {
        return redisTemplate.hasKey(key);
    }
    @Override
    public void setRedisKeyToTaskVO(String key, TaskHandleVO taskVO, long expireTime, TimeUnit unit) throws ServiceException {
        ValueOperations<String, TaskHandleVO> operations = redisTemplate.opsForValue();
        operations.set(key, taskVO, expireTime, unit);
    }

    @Override
    public TaskHandleVO getRedisKeyToTaskVO(String key) throws ServiceException {
        ValueOperations<String, TaskHandleVO> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }
}
