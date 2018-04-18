/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:springboot-test  
 * File Name:Student.java  
 * Package Name:com.lixy.bean
 * Author   Joe
 * Date:2017年11月7日上午9:21:28
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.bean;

import java.util.Date;

/**  
 * ClassName:Student 
 * Date:     2017年11月7日 上午9:21:28
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
public class Student {

	private int id;
	private String name;
	private int sex;
	private String address;
	private Date cDate;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getcDate() {
		return cDate;
	}
	public void setcDate(Date cDate) {
		this.cDate = cDate;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", sex=" + sex + ", address=" + address + ", cDate=" + cDate + "]";
	}
}