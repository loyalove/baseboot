package com.loyalove.baseboot.server;


import com.alibaba.dubbo.config.annotation.Service;
import com.loyalove.baseboot.common.model.Pager;
import com.loyalove.baseboot.dao.auth.UserDAO;
import com.loyalove.baseboot.pojo.UserPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Loyal on 2016/12/3.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDAO userDAO;

    /**
     * 查询用户列表
     *
     * @return
     */
    @Override
    public List<UserPO> queryAll() {
        return userDAO.queryUsers(new Pager());
    }
}
