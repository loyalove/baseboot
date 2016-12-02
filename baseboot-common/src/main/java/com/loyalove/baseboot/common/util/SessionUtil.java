package com.loyalove.baseboot.common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * Created by sailuo on 2016/10/23.
 */
public class SessionUtil {

    private SessionUtil() {
    }

    public static Session currentSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static void setAttribute(Object key, Object value) {
        currentSession().setAttribute(key, value);
    }

    public static Object getAttribute(Object key) {
        return currentSession().getAttribute(key);
    }

    public static void removeSetAttribute(Object key) {
        currentSession().removeAttribute(key);
    }

}
