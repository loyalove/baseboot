/**
 * 
 */
package com.loyalove.baseboot.common.util;

import java.io.Serializable;


public class PageBean implements Serializable {
	
	// 每页显示的行数
	private int		pageSize	= 10;
	
	// 请求的当前页
	private int		curPage;
	
	// 总页数
	private int		totalPages;
	
	// 总记录数
	private int		totalRecords;
	
	// 排序字段
	private String	orderBy;
	
	// 排序方式
	private String	order;
	
	// 分页开始
	private int		start;
	
	// 分页结束
	private int		end;
	
	/**
	 * 初始化PageBean相关参数
	 * 
	 * @param totalRecords
	 */
	public void init(int totalRecords) {
		if (curPage > 0 && pageSize > 0) {
			end = (curPage - 1) * pageSize + pageSize;
		}
		this.totalRecords = totalRecords;
		if (this.getTotalRecords() > 0)
			this.setTotalPages((this.getTotalRecords() - 1) / this.getPageSize() + 1);
		if (this.getCurPage() > this.getTotalRecords())
			this.setCurPage(this.getTotalRecords());
	}
	
	/**
	 * 获取需要查询的起始位置
	 * 
	 * @return
	 */
	public int getFirstNum() {
		if (curPage > 0 && pageSize > 0) {
			return (curPage - 1) * pageSize;
		}
		return 0;
	}
	
	/**
	 * 获取需要查询的起始位置
	 * 
	 * @return
	 */
	public int getLastNum() {
		if (curPage > 0 && pageSize > 0) {
			return (curPage - 1) * pageSize + pageSize;
		}
		return 0;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getCurPage() {
		return curPage;
	}
	
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	public int getTotalRecords() {
		return totalRecords;
	}
	
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	public String getOrderBy() {
		return orderBy;
	}
	
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public String getOrder() {
		return order;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}
	
	// 计算页码
	public void createPages() {
		int pagesize = getPageSize();
		int rows = getTotalRecords();
		
		int total = 0;
		if (rows >= pagesize) {
			total = rows % pagesize == 0 ? rows / pagesize : rows / pagesize + 1;
		} else {
			total = 1;
		}
		setTotalPages(total);
		
	}
	
	public int getStart() {
		if (curPage > 0 && pageSize > 0) {
			start = (curPage - 1) * pageSize;
		}
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getEnd() {
		if (curPage > 0 && pageSize > 0) {
			end = (curPage - 1) * pageSize + pageSize;
		}
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
}
