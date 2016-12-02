package com.loyalove.baseboot.common.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ranmin-zhouyuhong
 * 2016/11/15
 */
public class MD5Utils extends AbstractUtils {

    public static String encryptByMd5AndBase64(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        //确定计算方法
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            return base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error("MD5+base64加密异常，异常原因：" + e);
        }
        return null;
    }

    public static String encryptByMD5(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(str.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte aB : b) {
                i = aB;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            logger.error("MD5加密异常，异常原因：" + e);
        }
        return null;
    }

}
