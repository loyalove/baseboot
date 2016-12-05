package com.loyalove.baseboot.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loyalove.baseboot.pojo.UserPO;
import com.loyalove.baseboot.server.UserService;
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

    @Reference
    private UserService userService;

    /**
     * 查询用户列表
     * @return
     */
    @Override
    public List<UserPO> queryAll() {
        return userService.queryAll();
    }
}
