package com.loyalove.baseboot.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 
 * @author zhoubo@yiji.com
 * 
 */
public class ResultListUtil {
	private static Logger logger = LoggerFactory.getLogger(ResultListUtil.class);
	
	/**
	 * 获取结果集中的一个对象
	 * @param list
	 * @return 结果集为空，返回null；结果集大小为1，返回这个对象；结果集大小大于1，抛出异常
	 */
	public static <T> T getSingleResult(List<T> list) {
		
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
}
