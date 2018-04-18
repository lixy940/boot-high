/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:spring-boot-learn  
 * File Name:TargetDataSource.java  
 * Package Name:com.lixy.dynamicDataSource
 * Author   Joe
 * Date:2017年11月13日下午7:42:15
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.dynamicDataSource;

import java.lang.annotation.*;

/**  
 * ClassName:TargetDataSource 
 * Date:     2017年11月13日 下午7:42:15
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String name();
}
