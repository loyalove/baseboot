package com.loyalove.baseboot.app.auth;

import com.loyalove.baseboot.common.model.Pager;
import com.loyalove.baseboot.pojo.UserPO;
import com.loyalove.baseboot.vo.UserVO;

import java.util.List;

/**
 * Title: UserServiceClient.java
 * Description: UserServiceClient
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-12-06 11:07
 */
public interface UserServiceClient {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    UserPO queryUserByName(String username);

    /**
     * 根据用户查询角色和权限
     * @param userPO
     * @return
     */
    UserVO queryUserRolePermission(UserPO userPO);

    /**
     * 查询用户列表
     * @return
     */
    List<UserPO> queryUsers(Pager pager);
}
