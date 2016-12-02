package com.loyalove.baseboot.common.enums;

/**
 * @author  zhoubo@yiji.com
 * @date 2016/11/15
 */
public enum WeekEnum {

    /**
     * 星期一
     */
    MONDAY("MONDAY", "星期一"),
    /**
     * 星期二
     */
    TUESDAY("TUESDAY", "星期二"),
    /**
     * 星期三
     */
    WEDNESDAY("WEDNESDAY", "星期三"),
    /**
     * 星期四
     */
    THURSDAY("THURSDAY", "星期四"),
    /**
     * 星期五
     */
    FRIDAY("FRIDAY", "星期五"),
    /**
     * 星期六
     */
    SATURDAY("SATURDAY", "星期六"),
    /**
     * 星期日
     */
    SUNDAY("SUNDAY", "星期日");



    /**
     * 枚举值
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String message;

    /**
     * @param code    枚举值
     * @param message 枚举描述
     */
    private WeekEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Returns the code.
     */
    public String code() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String message() {
        return message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return WeekEnum
     */
    public static WeekEnum getByCode(String code) {
        for (WeekEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<WeekEnum>
     */
    public static java.util.List<WeekEnum> getAllEnum() {
        java.util.List<WeekEnum> list = new java.util.ArrayList<WeekEnum>(values().length);
        for (WeekEnum _enum : values()) {
            list.add(_enum);
        }
        return list;
    }

    /**
     * 获取全部枚举值
     *
     * @return List<String>
     */
    public static java.util.List<String> getAllEnumCode() {
        java.util.List<String> list = new java.util.ArrayList<String>(values().length);
        for (WeekEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }

    /**
     * 通过code获取msg
     *
     * @param code 枚举值
     * @return
     */
    public static String getMsgByCode(String code) {
        if (code == null) {
            return null;
        }
        WeekEnum _enum = getByCode(code);
        if (_enum == null) {
            return null;
        }
        return _enum.getMessage();
    }

    /**
     * 获取枚举code
     *
     * @param _enum
     * @return
     */
    public static String getCode(WeekEnum _enum) {
        if (_enum == null) {
            return null;
        }
        return _enum.getCode();
    }
}
