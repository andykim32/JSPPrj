package com.newlecture.web.controller.admin.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;
import com.newlecture.web.service.NoticeService;

@WebServlet("/admin/board/notice/list")
public class ListController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String field_ = request.getParameter("f");
		String query_ = request.getParameter("q");
		String page_ = request.getParameter("p");
		
		String field = "title";
		if(field_ != null && !field_.equals("")) {
			field = field_;
		}
		String query = "";
		if(query_ != null && !query_.equals("")) {
			 query =  query_;
		}
		int page = 1;
		if(page_ != null && !page_.equals("")) {
			page = Integer.parseInt(page_);
		}

		NoticeService service = new NoticeService();
		List<NoticeView> noticeList = service.getNoticeList(field,query,page);
		
		int count = service.getNoticeCount(field, query);

		request.setAttribute("list", noticeList);
		request.setAttribute("count", count);
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp").forward(request, response);

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] openId_ = request.getParameterValues("open-id");
		String[] delId_ = request.getParameterValues("del-id");
		String cmd =  request.getParameter("cmd");
		switch (cmd) {
		case "일괄공개":
			int[] openId = new int[openId_.length];
			for(int i=0;i<openId_.length;i++) {
				openId[i] = Integer.parseInt(openId_[i]);
			}
			break;
		case "일괄삭제":
			int[] delIds = new int[delId_.length];
			for(int i=0;i<delId_.length;i++) {
				delIds[i] = Integer.parseInt(delId_[i]);
			}
			NoticeService service = new NoticeService();
			int result = service.removeNoticeAll(delIds);
			break;
		}
		response.sendRedirect("/admin/board/notice/list");
		
	}
}