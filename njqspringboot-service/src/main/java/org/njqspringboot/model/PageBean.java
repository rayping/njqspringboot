package org.njqspringboot.model;

import java.io.Serializable;

public class PageBean implements Serializable{
	private static final long serialVersionUID = -7914381419605661364L;
	
	private int startIndex = 0;
	
	private int pageSize = 10;

	private int pageNo = 1;
	
	public PageBean() {
		startIndex = (pageNo - 1) * pageSize;
	}
	
	public int getStartIndex() {
		startIndex = (pageNo - 1) * pageSize;
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageIndex) {
		this.pageNo = pageIndex;
	}

	@Override
	public String toString() {
		return "PageBean{" +
				"startIndex=" + startIndex +
				", pageSize=" + pageSize +
				", pageNo=" + pageNo +
				'}';
	}

}
