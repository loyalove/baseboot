package com.loyalove.baseboot.vo;

import com.loyalove.baseboot.pojo.UserPO;

import java.util.Set;

/**
 * Title: UserVO.java
 * Description: UserVO
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-11-30 9:46
 */
public class UserVO {
    private UserPO userPO;

    private Set<String> roles;

    private Set<String> permissions;

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
