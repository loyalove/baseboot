package com.loyalove.baseboot.service.auth;

import com.alibaba.dubbo.config.annotation.Service;
import com.loyalove.baseboot.api.auth.UserService;
import com.loyalove.baseboot.biz.auth.UserBiz;
import com.loyalove.baseboot.common.model.Pager;
import com.loyalove.baseboot.pojo.UserPO;
import com.loyalove.baseboot.service.BaseService;
import com.loyalove.baseboot.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Title: UserServiceImpl.java
 * Description: UserServiceImpl
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-11-30 8:56
 */
@Service(interfaceClass = UserService.class, version = "1.0")
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired(required = true)
    UserBiz userBiz;

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Override
    public UserPO queryUserByName(String username) {
        return userBiz.queryUserByName(username);
    }

    /**
     * 根据用户查询角色和权限
     * @param userPO
     * @return
     */
    @Override
    public UserVO queryUserRolePermission(UserPO userPO) {
        return userBiz.queryUserRolePermission(userPO);
    }

    /**
     * 查询用户列表
     * @return
     * @param pager
     */
    @Override
    public List<UserPO> queryUsers(Pager pager) {
        return userBiz.queryUsers(pager);
    }
}
