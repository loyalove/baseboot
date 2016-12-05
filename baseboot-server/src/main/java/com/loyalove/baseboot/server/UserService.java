package com.loyalove.baseboot.server;

import com.loyalove.baseboot.pojo.UserPO;

import java.util.List;

/**
 * Created by Loyal on 2016/12/3.
 */
public interface UserService {

    /**
     * 查询用户列表
     * @return
     */
    List<UserPO> queryAll();
}