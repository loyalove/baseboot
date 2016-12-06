package com.loyalove.baseboot.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loyalove.baseboot.api.auth.UserService;
import com.loyalove.baseboot.common.model.Pager;
import com.loyalove.baseboot.pojo.UserPO;
import com.loyalove.baseboot.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: EchoServiceClientImpl.java
 * Description: EchoServiceClientImpl
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-12-05 14:32
 */
@Service
public class UserServiceClientImpl implements UserServiceClient {

    @Reference(interfaceClass = UserService.class, version = "1.0")
    private UserService userService;

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public UserPO queryUserByName(String username) {
        return userService.queryUserByName(username);
    }

    /**
     * 根据用户查询角色和权限
     * @param userPO
     * @return
     */
    public UserVO queryUserRolePermission(UserPO userPO) {
        return userService.queryUserRolePermission(userPO);
    }

    /**
     * 查询用户列表
     * @return
     * @param pager
     */
    public List<UserPO> queryUsers(Pager pager) {
        return userService.queryUsers(pager);
    }
}
