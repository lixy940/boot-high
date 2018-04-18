/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:springboot-test  
 * File Name:IStudentDao.java  
 * Package Name:com.lixy.dao
 * Author   Joe
 * Date:2017年11月7日上午9:22:59
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.dao;


import com.lixy.boothigh.bean.OrderDetail;

import java.util.List;
import java.util.Map;

/**  
 * IOrderDetailDao 
 * Date:     2017年11月7日 上午9:22:59
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
public interface IOrderDetailDao {

	/**
	 * findOrderDetail:(查询List).  
	 * @author Joe
	 * Date:2017年11月7日上午9:30:37
	 *
	 * @param map
	 * @return
	 */
	List<OrderDetail> findOrderDetail(Map<String, Object> map);
	
	/**
	 * addOrderDetail:(增加).  
	 * @author Joe
	 * Date:2017年11月7日上午9:30:48
	 *
	 * @param detail
	 * @return
	 */
	boolean addOrderDetail(OrderDetail detail);
	
}
  
