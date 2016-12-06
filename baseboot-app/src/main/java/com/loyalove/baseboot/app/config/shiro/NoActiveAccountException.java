package com.loyalove.baseboot.app.config.shiro;

import org.apache.shiro.authc.AccountException;

/**
 * Title: NoActiveAccountException.java
 * Description: NoActiveAccountException
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-11-30 9:10
 */
public class NoActiveAccountException extends AccountException {
    public NoActiveAccountException() {
    }

    public NoActiveAccountException(String message) {
        super(message);
    }

    public NoActiveAccountException(Throwable cause) {
        super(cause);
    }

    public NoActiveAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
