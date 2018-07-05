package com.lixy.boothigh.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限限制,默认都是false
 *
 * @author MR LIS 2018-7-5 18:29:02
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermessionLimit {

	/**
	 * 要求管理员权限
	 *
	 * @return
	 */
	boolean adminuser() default false;

	/**
	 * 超级管理员
	 *
	 * @return
	 */
	boolean superAdminUser() default false;

}