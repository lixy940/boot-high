/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:springboot-test  
 * File Name:StudentServiceImpl.java  
 * Package Name:com.lixy.service
 * Author   Joe
 * Date:2017年11月7日上午9:59:13
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.service.impl;

import com.lixy.boothigh.bean.Student;
import com.lixy.boothigh.dao.IStudentDao;
import com.lixy.boothigh.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**  
 * ClassName:StudentServiceImpl 
 * Date:     2017年11月7日 上午9:59:13
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
@Service("studentService")
public class StudentServiceImpl implements IStudentService {

	@Autowired
	private IStudentDao studentDao;
	
	@Override
	public List<Student> findStudents(Map<String, Object> map) {
		return studentDao.findStudents(map);
	}

	@Override
	public boolean addStudent(Student student) {
		return studentDao.addStudent(student);
	}
	
}