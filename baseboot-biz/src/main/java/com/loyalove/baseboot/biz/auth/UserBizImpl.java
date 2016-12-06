package com.loyalove.baseboot.biz.auth;

import com.loyalove.baseboot.biz.BaseBiz;
import com.loyalove.baseboot.common.model.Pager;
import com.loyalove.baseboot.common.util.CollectionUtils;
import com.loyalove.baseboot.dao.auth.UserDAO;
import com.loyalove.baseboot.dao.base.UserMapper;
import com.loyalove.baseboot.pojo.UserExample;
import com.loyalove.baseboot.pojo.UserPO;
import com.loyalove.baseboot.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: UserServiceImpl.java
 * Description: UserServiceImpl
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-11-30 8:56
 */
@Service
public class UserBizImpl extends BaseBiz implements UserBiz {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserDAO userDAO;

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    @Override
    public UserPO queryUserByName(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UserPO> userPOS = userMapper.selectByExample(example);
        return CollectionUtils.isEmpty(userPOS) ? null : userPOS.get(0);
    }

    /**
     * 根据用户查询角色和权限
     *
     * @param userPO
     * @return
     */
    @Override
    public UserVO queryUserRolePermission(UserPO userPO) {
        UserVO userVO = new UserVO();
        userVO.setUserPO(userPO);
        userVO.setRoles(userDAO.queryRoleByUserId(userPO.getUserId()));
        userVO.setPermissions(userDAO.queryPermissionsByUserId(userPO.getUserId()));
        return userVO;
    }

    /**
     * 查询用户列表
     *
     * @param pager
     * @return
     */
    @Override
    public List<UserPO> queryUsers(Pager pager) {
        return userDAO.queryUsers(pager);
    }
}
