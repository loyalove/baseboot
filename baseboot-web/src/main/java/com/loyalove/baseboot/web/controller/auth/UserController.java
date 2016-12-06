package com.loyalove.baseboot.web.controller.auth;

import com.loyalove.baseboot.common.model.Pager;
import com.loyalove.baseboot.web.client.auth.UserServiceClient;
import com.loyalove.baseboot.web.controller.BaseController;
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
    UserServiceClient serviceClient;

    @RequestMapping("")
    public Object queryUsers(Pager pager) {
        Map<String, Object> result = new HashMap<>();
        result.put("list", serviceClient.queryUsers(new Pager()));
        result.put("count", 0);
        return result;
    }
}
