package com.newlecture.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.Buffer;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

@MultipartConfig(
		fileSizeThreshold = 1024*1024,
		maxFileSize=1024*1024*50,
		maxRequestSize = 1024*1024*50*5
)
@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String isOpen = request.getParameter("open");
		boolean pub = false;
		if(isOpen != null) {
			pub = true;
		}
		
		String realPath = request.getServletContext().getRealPath("/upload");
		Part part = request.getPart("file");
		String filePath = realPath + File.separator + part.getSubmittedFileName();
		System.out.println(filePath);
		InputStream fis =  part.getInputStream();
		FileOutputStream fos = new FileOutputStream(filePath);
		byte[] b = new byte[1024];
		int size = 0;
		while((size = fis.read(b)) != -1) {
			fos.write(b,0,size);
		}
		fos.close();
		fis.close();
		
		
		
		NoticeService service = new NoticeService();
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterId("andy");
		service.insertNotice(notice);
		
		response.sendRedirect("list");
	}
}
