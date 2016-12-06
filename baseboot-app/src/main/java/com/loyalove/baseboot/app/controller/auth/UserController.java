package com.loyalove.baseboot.app.controller.auth;

import com.loyalove.baseboot.common.model.Pager;
import com.loyalove.baseboot.app.controller.BaseController;
import com.loyalove.baseboot.dao.base.UserMapper;
import com.loyalove.baseboot.pojo.UserExample;
import com.loyalove.baseboot.api.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: UserController.java
 * Description: UserController
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-11-30 19:30
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @RequestMapping("")
    public Object queryUsers(Pager pager) {
        Map<String, Object> result = new HashMap<>();
        UserExample example = new UserExample();
        example.setLimit(1);
        example.setLimitStart(0);
        result.put("list", userMapper.selectByExample(example));
        result.put("count", userMapper.countByExample(example));
        return result;
    }
}
