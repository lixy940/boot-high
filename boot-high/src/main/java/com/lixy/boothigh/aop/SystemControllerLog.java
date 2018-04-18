package com.lixy.boothigh.aop;

import java.lang.annotation.*;

/**
 * @Author: MR LIS
 * @Description: 自定义注解 拦截Controller
 * @Date: 14:32 2018/4/18
 * @return
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface SystemControllerLog {
    /**
     * 方法描述
     * @return
     */
    String methodDesc();

}