package com.loyalove.baseboot.client;

import com.loyalove.baseboot.pojo.UserPO;

import java.util.List;

/**
 * Created by Loyal on 2016/12/3.
 */
public interface UserServiceClient {
    /**
     * 查询用户列表
     * @return
     */
    List<UserPO> queryAll();
}