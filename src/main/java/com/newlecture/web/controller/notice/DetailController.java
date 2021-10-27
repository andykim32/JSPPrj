package com.newlecture.web.controller.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

@WebServlet("/notice/detail")
public class DetailController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id_ = request.getParameter("id");
		int id = 0;
		if (id_ != null && !id_.equals("")) {
			id = Integer.parseInt(id_);
		}

		NoticeService service = new NoticeService();

		Notice notice = service.getNotice(id);

		request.setAttribute("n", notice);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp");
		dispatcher.forward(request, response);
			
	}
}
