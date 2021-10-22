package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add2")
public class Add2 extends HttpServlet  {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String[] nums = request.getParameterValues("num");
		
		int result = 0;
		for (String num  : nums) {
			if(num != null && ! num.equals(""))
				result += Integer.parseInt(num);
		}		
		
		PrintWriter out = response.getWriter();
		out.printf("µ¡¼À °á°ú : %d ", result);
	}
}
