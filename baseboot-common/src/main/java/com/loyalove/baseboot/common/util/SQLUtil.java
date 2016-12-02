package com.loyalove.baseboot.common.util;

/**
 * 
 * @Filename SQLUtil.java
 * 
 * @Description sql模糊查看字段值编辑
 *
 */
public class SQLUtil {
	/**
	 * 构建模糊查询参数，如：%abc%
	 * @param param
	 * @return
	 */
	public static String buildLikeParam(String param) {
		if (StringUtils.isBlank(param)) {
			return null;
		}
		param = param.replaceAll("%", "/%");
		return String.format("%%%s%%", param);
	}
}
