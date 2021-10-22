package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/calc2")
public class Calc2 extends HttpServlet  {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String v_ = request.getParameter("v");
		String op = request.getParameter("operator");
		
		int v = 0;
		
		if(v_ != null && ! v_.equals(""))
			v = Integer.parseInt(v_);
		
		ServletContext application = request.getServletContext();
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		
		if(op.equals("=")) {
			//int x = (Integer)application.getAttribute("value");
			//int x = (Integer)session.getAttribute("value");\
			int x = 0;
			String operator = "";
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("op")) 
					operator = cookie.getValue();
				else if (cookie.getName().equals("value"))
					x = Integer.parseInt(cookie.getValue());				
			}
			int y = v;		
			//String operator = (String)application.getAttribute("op");
			//String operator = (String)session.getAttribute("op");
			int result = 0;
			if(operator.equals("+")) {
				result = x + y;
			}else {
				result = x - y;
			}
			PrintWriter out = response.getWriter();			
			out.printf("°á°ú : %d " , result);			
		}else {
			//application.setAttribute("value", v);
			//application.setAttribute("op", op);
			//session.setAttribute("value", v);
			//session.setAttribute("op", op);
			Cookie valueCookie = new Cookie("value", String.valueOf(v));			
			Cookie opCookie = new Cookie("op", op);			
			response.addCookie(valueCookie);
			response.addCookie(opCookie);		
			
		}	
	}
}
