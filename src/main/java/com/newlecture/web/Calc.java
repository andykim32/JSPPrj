package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calc")
public class Calc extends HttpServlet  {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String x_ = request.getParameter("x");
		String y_ = request.getParameter("y");
		String operator = request.getParameter("operator");
		
		int x = 0, y = 0;
		
		if(x_ != null && ! x_.equals(""))
			x = Integer.parseInt(x_);
		if(y_ != null && !y_.equals(""))
			y = Integer.parseInt(y_);
		
		PrintWriter out = response.getWriter();
		if(operator.equals("µ¡¼À"))
			out.printf("µ¡¼À °á°ú : %d " , (x+y));
		else if(operator.equals("»¬¼À"))
			out.printf("»¬¼À °á°ú : %d " , (x-y));
	}
}
