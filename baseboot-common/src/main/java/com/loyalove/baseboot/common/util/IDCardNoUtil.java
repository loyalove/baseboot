package com.loyalove.baseboot.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Filename IDCardNoUtil.java
 * @Description
 */
public class IDCardNoUtil {
    private static Map<String, String> AREA = initArea();
    private static String[] ERRORS = {"身份证号码位数不对!", "身份证号码出生日期超出范围或含有非法字符!",
            "身份证号码校验错误!", "身份证地区非法!", "身份证检查异常"};
    private static String VERIFY_CODES = "10X98765432";

    private static Map<Integer, String> checkMap = new HashMap<Integer, String>();

    static {
        checkMap.put(15, "checkCardFor15");
        checkMap.put(18, "checkCardFor18");
    }

    /**
     * 判断身份证号格式和校验位合法性
     *
     * @param idCardNo
     * @return
     */
    public static String checkIdCardNo(String idCardNo) {
        if (idCardNo == null || (idCardNo.length() != 15 && idCardNo.length() != 18)) {
            return ERRORS[0];
        }

        String areaCode = idCardNo.substring(0, 2);
        if (AREA.get(areaCode) == null) {
            return ERRORS[3];
        }


        if (!checkMap.containsKey(idCardNo.length())) {
            return ERRORS[0];
        }

        try {
            return (String) IDCardNoUtil.class.getDeclaredMethod(checkMap.get(idCardNo.length()), String.class).invoke(null, idCardNo);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ERRORS[4];


    }

    private static String checkCardFor15(String idCardNo) {
        String ereg = "";
        int year = Integer.parseInt(idCardNo.substring(6, 2)) + 1900;
        if (year % 4 == 0 || (year % 100 == 0 && year % 4 == 0)) {
            ereg = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$"; //测试出生日期的合法性
        } else {
            ereg = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$"; //测试出生日期的合法性
        }
        Pattern p = Pattern.compile(ereg);
        if (!p.matcher(idCardNo).matches()) {
            return ERRORS[1];
        }

        return null;
    }

    private static String checkCardFor18(String idCardNo) {
        String ereg = "";
        //18位身份号码检测
        //出生日期的合法性检查
        //闰年月日,((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
        //平年月日,((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
        int year = Integer.parseInt(idCardNo.substring(6, 10));
        if (year % 4 == 0 || (year % 100 == 0 && year % 4 == 0)) {
            ereg = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$"; //闰年出生日期的合法性正则表达式
        } else {
            ereg = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$"; //平年出生日期的合法性正则表达式
        }

        Pattern p = Pattern.compile(ereg);
        if (!p.matcher(idCardNo).matches()) {
            return ERRORS[1];
        }

        //计算校验位
        char[] cardArray = idCardNo.toCharArray();
        int[] factor = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (cardArray[i] - 48) * factor[i];
        }
        int index = sum % 11;

        //判断校验位
        char verifyCode = VERIFY_CODES.charAt(index);
        if (verifyCode != cardArray[17]) {
            return ERRORS[2];
        }
        return null;
    }

    private static Map<String, String> initArea() {
        Map<String, String> areaMap = new HashMap<String, String>();
        areaMap.put("11", "北京");
        areaMap.put("12", "天津");
        areaMap.put("13", "河北");
        areaMap.put("14", "山西");
        areaMap.put("15", "内蒙古");
        areaMap.put("21", "辽宁");
        areaMap.put("22", "吉林");
        areaMap.put("23", "黑龙江");
        areaMap.put("31", "上海");
        areaMap.put("32", "江苏");
        areaMap.put("33", "浙江");
        areaMap.put("34", "安徽");
        areaMap.put("35", "福建");
        areaMap.put("36", "江西");
        areaMap.put("37", "山东");
        areaMap.put("41", "河南");
        areaMap.put("42", "湖北");
        areaMap.put("43", "湖南");
        areaMap.put("44", "广东");
        areaMap.put("45", "广西");
        areaMap.put("46", "海南");
        areaMap.put("50", "重庆");
        areaMap.put("51", "四川");
        areaMap.put("52", "贵州");
        areaMap.put("53", "云南");
        areaMap.put("54", "西藏");
        areaMap.put("61", "陕西");
        areaMap.put("62", "甘肃");
        areaMap.put("63", "青海");
        areaMap.put("64", "宁夏");
        areaMap.put("65", "新疆");
        areaMap.put("71", "台湾");
        areaMap.put("81", "香港");
        areaMap.put("82", "澳门");
        areaMap.put("91", "国外");
        return areaMap;
    }
}
