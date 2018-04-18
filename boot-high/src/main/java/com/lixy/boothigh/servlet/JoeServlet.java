/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:springboot-test  
 * File Name:XbqServlet.java  
 * Package Name:com.lixy.servlet
 * Author   Joe
 * Date:2017年11月6日下午5:13:10
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**  
 * ClassName:XbqServlet 
 * 通过  @WebServlet 注解 整合Servlet
 * Date:     2017年11月6日 下午5:13:10
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
@WebServlet(urlPatterns = "/joe/*")
public class JoeServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");  
        PrintWriter out = resp.getWriter();  
        out.println("<html>");  
        out.println("<head>");  
        out.println("<title>Hello World</title>");  
        out.println("</head>");  
        out.println("<body><center>");  
        out.println("<h3>我是通过   @WebServlet 注解注册Servlet的</h3>");  
        out.println("</center></body>");  
        out.println("</html>"); 
	}
}