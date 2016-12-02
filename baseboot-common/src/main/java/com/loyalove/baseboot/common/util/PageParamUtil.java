/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.loyalove.baseboot.common.util;


/**
 *                       
 * @Filename PageParamUtil.java
 *
 * @Description 
 *
 *
 */
public class PageParamUtil {
	
	private static final int	DEFAULTPAGESIZE	= 10;
	
	/**
	 * * 校正非法page对象参数
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static PageParam convertParam(String pageNo, String pageSize) {
		PageParam pageParam = new PageParam();
		int iPageNo = 1;
		int iPageSize = DEFAULTPAGESIZE;
		try {
			iPageNo = Integer.valueOf(pageNo);
			if (iPageNo < 1)
				iPageNo = 1;
		} catch (Exception e) {
		}
		try {
			iPageSize = Integer.valueOf(pageSize);
			if (iPageSize < 1)
				iPageSize = DEFAULTPAGESIZE;
		} catch (Exception e) {
		}
		pageParam.setPageNo(iPageNo);
		pageParam.setPageSize(iPageSize);
		return pageParam;
	}
	
	/**
	 * 校正非法page对象参数
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static PageParam convertParam(int pageNo, int pageSize) {
		PageParam pageParam = new PageParam();
		if (pageNo < 1)
			pageNo = 1;
		if (pageSize < 1)
			pageSize = DEFAULTPAGESIZE;
		pageParam.setPageNo(pageNo);
		pageParam.setPageSize(pageNo);
		return pageParam;
	}
	
	/**
	 * 获取分页条件的起始数
	 * 
	 * @param totalSize
	 *            总页数
	 * @param pageSize
	 *            每页显示几条数据
	 * @param pageNo
	 *            第几页
	 * @return
	 */
	public static int startValue(int totalSize, int pageSize, int pageNo) {

		// 数据总页数
		int totalPage = 0;
		int start = 0;
		if (totalSize % pageSize == 0)
			totalPage = totalSize / pageSize;
		else
			totalPage = totalSize / pageSize + 1;
		if (totalPage < pageNo && totalPage > 0) {// 总页数小于跳转页数，则跳转到最后一页
			start = (totalPage - 1) * pageSize;
		} else if (pageNo <= 0) {
			start = 0;
		} else {
			start = (pageNo - 1) * pageSize;
		}
		return start;
	}
}
