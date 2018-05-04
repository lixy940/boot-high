package com.lixy.boothigh;

import com.lixy.boothigh.service.RedisService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 11:09 2018/5/4
 * @Modified By:
 */
public class RedisServcieTest extends BootHighApplicationTests{



    @Autowired
    private RedisService redisService;

    @Test
    public void  insertData(){
        redisService.setKeyToValueObj("aa",12);
        System.out.println("result--->"+redisService.getValueByKeyObj("aa"));
        System.out.println(redisService.hasKey("aa"));
    }
}
