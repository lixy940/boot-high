/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:springboot-test  
 * File Name:StudentController.java  
 * Package Name:com.lixy.controller
 * Author   Joe
 * Date:2017年11月6日下午4:27:40
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.controller;

import com.lixy.boothigh.bean.Student;
import com.lixy.boothigh.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**  
 * ClassName:StudentController 
 * Date:     2017年11月6日 下午4:27:40
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
@Controller
public class StudentController {
	
	@Autowired
	private IStudentService studentService;

	/**
	 * view:(跳转到JSP界面).  
	 * @author Joe
	 * Date:2017年11月6日下午4:29:27
	 *
	 * @param map
	 * @return
	 */
	@RequestMapping(value = {"/", "/view"})
	public String view(Map<String, Object> map) {
		map.put("name", "SpringBoot");
		map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return "index";
	}
	
	/**
	 * freemarker:(跳转到 freemarker.ftl).  
	 * @author Joe
	 * Date:2017年11月6日下午4:52:19
	 *
	 * @param map
	 * @return
	 */
	@RequestMapping("/freemarker")
	public String freemarker(Map<String, Object> map){
		map.put("name", "Joe");
        map.put("sex", 1);	//sex:性别，1：男；0：女；  
        
        // 模拟数据
        List<Map<String, Object>> friends = new ArrayList<Map<String, Object>>();
        Map<String, Object> friend = new HashMap<String, Object>();
        friend.put("name", "xbq");
        friend.put("age", 22);
        friends.add(friend);
        friend = new HashMap<String, Object>();
        friend.put("name", "July");
        friend.put("age", 18);
        friends.add(friend);
        map.put("friends", friends);
		return "freemarker";
	}
	
	/**
	 * list:(查询全部).  
	 * @author Joe
	 * Date:2017年11月7日上午10:09:00
	 *
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(){
		List<Student> list = studentService.findStudents(null);
		return list;
	}
	
}