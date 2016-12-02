package com.loyalove.baseboot.common.util;

/**
 *                       
 * @Filename PageParam.java
 *
 * @Description 
 */
public class PageParam {
	//第几页
	int	pageNo;
	//页面大小
	int	pageSize;
	
	public PageParam() {
	}
	
	public PageParam(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
