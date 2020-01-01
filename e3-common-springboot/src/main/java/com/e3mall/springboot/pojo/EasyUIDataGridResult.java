package com.e3mall.springboot.pojo;

import java.io.Serializable;
import java.util.List;

public class EasyUIDataGridResult implements Serializable{

	private static final long serialVersionUID = -4009345772527190807L;
	
	/*
	 * 总页数 
	 */
	private long total;
	/**
	 * 数据
	 */
	private List rows;
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}
