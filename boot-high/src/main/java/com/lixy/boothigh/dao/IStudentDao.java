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


import com.lixy.boothigh.bean.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**  
 * ClassName:IStudentDao 
 * Date:     2017年11月7日 上午9:22:59
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
@Repository
public interface IStudentDao {

	/**
	 * findStudents:(查询List).  
	 * @author Joe
	 * Date:2017年11月7日上午9:30:37
	 *
	 * @param map
	 * @return
	 */
	List<Student> findStudents(Map<String, Object> map);
	
	/**
	 * addStudent:(增加).  
	 * @author Joe
	 * Date:2017年11月7日上午9:30:48
	 *
	 * @param student
	 * @return
	 */
	boolean addStudent(Student student);
	
}
  
