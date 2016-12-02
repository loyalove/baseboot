package com.loyalove.baseboot.common.enums;

/**
 * Title: UserStatusEnum.java
 * Description: UserStatusEnum
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-11-30 9:05
 */
public enum UserStatusEnum {
    /**
     * 初始化
     */
    INIT("INIT", "初始化"),
    /**
     * 激活
     */
    ACTIVE("ACTIVE", "激活"),
    /**
     * 锁定
     */
    LOCKED("LOCKED", "锁定"),
    /**
     * 删除
     */
    DELETE("DELETE", "删除");


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
    private UserStatusEnum(String code, String message) {
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
     * @return UserStatusEnum
     */
    public static UserStatusEnum getByCode(String code) {
        for (UserStatusEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<UserStatusEnum>
     */
    public static java.util.List<UserStatusEnum> getAllEnum() {
        java.util.List<UserStatusEnum> list = new java.util.ArrayList<UserStatusEnum>(values().length);
        for (UserStatusEnum _enum : values()) {
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
        for (UserStatusEnum _enum : values()) {
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
        UserStatusEnum _enum = getByCode(code);
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
    public static String getCode(UserStatusEnum _enum) {
        if (_enum == null) {
            return null;
        }
        return _enum.getCode();
    }
}
