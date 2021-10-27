package com.newlecture.web.entity;

import java.util.Date;

public class NoticeView extends Notice {
	
	private int cmtCount;
	
	public NoticeView() {
		
	}

	public NoticeView(int idx, String title, String writerId, Date regdate, String hit, String files, boolean pub, int cmtCount) {
		super(idx, title, writerId, regdate, hit, files, "", pub);
		this.cmtCount = cmtCount;
	}

	public int getCmtCount() {
		return cmtCount;
	}

	public void setCmtCount(int cmtCount) {
		this.cmtCount = cmtCount;
	}

}
