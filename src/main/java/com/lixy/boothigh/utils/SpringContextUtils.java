package com.lixy.boothigh.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: MR LIS
 * @Description: spring容器
 * @Date: 10:57 2018/4/25
 * @return
 */
@Component
public class SpringContextUtils implements ApplicationContextAware{

    private static ApplicationContext applicationContext = null;
// 非@import显式注入，@Component是必须的，且该类必须与main同包或子包
    // 若非同包或子包，则需手动import 注入，有没有@Component都一样
    // 可复制到Test同包测试

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if(SpringContextUtils.applicationContext == null){
            SpringContextUtils.applicationContext  = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);

    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}
