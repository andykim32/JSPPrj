package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;

public class NoticeService {
	public int removeNoticeAll(int[] ids){
		int result = 0;
		String params = "";
		for(int i=0;i<ids.length;i++) {
			params += ids[i];
			if(i < ids.length-1) {
				params += ",";
			}
		}
		String sql = "DELETE FROM NOTICE WHERE ID IN("+params+")";
		String url = "jdbc:oracle:thin:@ndhope32.iptime.org:51521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "1234");
			Statement st = con.createStatement();		
			result =  st.executeUpdate(sql);
			st.close();
			con.close();					
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return result;
	}
	
	public int pubNoticeAll(int[] ids){
		return 0;
	}
	
	public int insertNotice(Notice notice) {
		int result = 0;
		
		String sql = "INSERT INTO NOTICE(TITLE,CONTENT,PUB,WRITER_ID) VALUES(?,?,?,?)";
		String url = "jdbc:oracle:thin:@ndhope32.iptime.org:51521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "1234");
			PreparedStatement ps = con.prepareStatement(sql);	
			ps.setString(1, notice.getTitle());
			ps.setString(2, notice.getContent());
			ps.setBoolean(3, notice.getPub());
			ps.setString(4, notice.getWriterId());
			result =  ps.executeUpdate();
			ps.close();
			con.close();					
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return result;
	}
	
	public int DeleteNotice(int id) {
		return 0;
	}
	
	public int updateNotice(Notice notice) {
		return 0;
	}
	
	public List<Notice> getNoticeNewestList(){
		return null;
	}
	
	public List<NoticeView> getNoticeList(){		
		return getNoticeList("title", "", 1); 
	}
	
	public List<NoticeView> getNoticeList(int page){		
		return getNoticeList("title", "", page); 
	}
	
	public List<NoticeView> getNoticeList(String field, String query, int page){
		List<NoticeView> list = new ArrayList<>();
		String sql = "SELECT * FROM ("
				+ "SELECT ROWNUM NUM, N.* "
				+ "FROM(SELECT * FROM NOTICE_V WHERE " + field +  " LIKE ? ORDER BY REGDATE DESC) N "
				+ ") WHERE NUM BETWEEN ? AND ?";
		
		String url = "jdbc:oracle:thin:@ndhope32.iptime.org:51521/xepdb1";
			
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "1234");
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%"+query+"%");			
			ps.setInt(2, 1+(page-1)*10);
			ps.setInt(3, page*10);
			ResultSet rs =  ps.executeQuery();
			
			while(rs.next()) {
				int idx = rs.getInt("id");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				//String content = rs.getString("CONTENT");
				int cmtCount = rs.getInt("CMT_COUNT");		
				boolean pub = rs.getBoolean("PUB");
				NoticeView notice = new NoticeView(idx,title,writerId,regdate,hit,files,pub,cmtCount);
				list.add(notice);
			}
			rs.close();
			ps.close();
			con.close();					
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return list;	
	}
	
	public int getNoticeCount() {
		return getNoticeCount("title", "");
	}
	
	public int getNoticeCount(String field, String query) {
		int count = 0;
		String sql = "SELECT COUNT(ID) AS COUNT FROM ("
				+ "SELECT ROWNUM NUM, N.* "
				+ "FROM(SELECT * FROM NOTICE WHERE " + field +  " LIKE ? ORDER BY REGDATE DESC) N "
				+ ") ";
		String url = "jdbc:oracle:thin:@ndhope32.iptime.org:51521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "1234");
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%"+query+"%");		
			
			ResultSet rs =  ps.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("COUNT");				
			}
			rs.close();
			ps.close();
			con.close();					
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return count;
	}
	
	public Notice getNotice(int id) {
		Notice notice = null;
		String sql = "SELECT * FROM NOTICE WHERE ID=?";
		String url = "jdbc:oracle:thin:@ndhope32.iptime.org:51521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "1234");
			PreparedStatement ps = con.prepareStatement(sql);			
			ps.setInt(1, id);
			ResultSet rs =  ps.executeQuery();
			
			if(rs.next()) {
				int idx = rs.getInt("id");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");
				
				notice = new Notice(idx,title,writerId,regdate,hit,files,content,pub);
	
			}
			rs.close();
			ps.close();
			con.close();					
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return notice;
	}
	
	public Notice getNextNotice(int id) {
		Notice notice = null;
		String sql = "SELECT * FROM("
				+ "SELECT * FROM "
				+ "NOTICE "
				+ "WHERE REGDATE > (SELECT REGDATE FROM NOTICE WHERE ID=?) "
				+ "ORDER BY REGDATE ASC) "
				+ "WHERE ROWNUM = 1";
		String url = "jdbc:oracle:thin:@ndhope32.iptime.org:51521/xepdb1";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "1234");
			PreparedStatement ps = con.prepareStatement(sql);			
			ps.setInt(1, id);
			ResultSet rs =  ps.executeQuery();
			
			if(rs.next()) {
				int idx = rs.getInt("id");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");
				
				notice = new Notice(idx,title,writerId,regdate,hit,files,content,pub);
	
			}
			rs.close();
			ps.close();
			con.close();					
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return notice;
	}
	
	public Notice getPrevNotice(int id) {
		Notice notice = null;
		String sql = "SELECT * FROM("
				+ "SELECT * FROM "
				+ "NOTICE "
				+ "WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID=?) "
				+ "ORDER BY REGDATE DESC) "
				+ "WHERE ROWNUM = 1";
		String url = "jdbc:oracle:thin:@ndhope32.iptime.org:51521/xepdb1";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "1234");
			PreparedStatement ps = con.prepareStatement(sql);			
			ps.setInt(1, id);
			ResultSet rs =  ps.executeQuery();
			
			if(rs.next()) {
				int idx = rs.getInt("id");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");
				
				notice = new Notice(idx,title,writerId,regdate,hit,files,content,pub);
	
			}
			rs.close();
			ps.close();
			con.close();					
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return notice;
	}
}
