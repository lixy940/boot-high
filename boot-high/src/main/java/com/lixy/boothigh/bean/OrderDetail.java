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
 * ClassName:OrderDetail
 * Date:     2017年11月13日 下午6:49:32
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
public class OrderDetail {
	
	private int id;
	private int orderId;
	private int goodsId;
	private String goodsNum;
	private Date cDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}
	public Date getcDate() {
		return cDate;
	}
	public void setcDate(Date cDate) {
		this.cDate = cDate;
	}
	
	@Override
	public String toString() {
		return "OrderDetail [id=" + id + ", orderId=" + orderId + ", goodsId=" + goodsId + ", goodsNum=" + goodsNum + ", cDate=" + cDate + "]";
	}
}