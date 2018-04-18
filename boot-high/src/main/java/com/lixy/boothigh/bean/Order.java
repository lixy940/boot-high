/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:spring-boot-learn  
 * File Name:Order.java  
 * Package Name:com.lixy.bean
 * Author   Joe
 * Date:2017年11月13日下午6:49:32
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.bean;

import java.util.Date;

/**  
 * ClassName:Order 
 * Date:     2017年11月13日 下午6:49:32
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
public class Order {
	
	private int id;
	private String orderNo;
	private String name;
	private double price;
	private Date cDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getcDate() {
		return cDate;
	}

	public void setcDate(Date cDate) {
		this.cDate = cDate;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderNo=" + orderNo + ", name=" + name + ", price=" + price + ", cDate=" + cDate + "]";
	}
}