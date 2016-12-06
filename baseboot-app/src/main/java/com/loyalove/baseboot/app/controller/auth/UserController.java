package com.loyalove.baseboot.app.controller.auth;

import com.loyalove.baseboot.api.auth.UserService;
import com.loyalove.baseboot.app.auth.UserServiceClient;
import com.loyalove.baseboot.app.controller.BaseController;
import com.loyalove.baseboot.common.model.Pager;
import com.loyalove.baseboot.common.model.Result;
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
    UserServiceClient userServiceClient;

    @RequestMapping("")
    public Result queryUsers(Pager pager) {
        Result result = Result.getResultSuccess("查询成功");
        result.setPager(pager);
        result.setResult(userServiceClient.queryUsers(pager));
        return result;
    }
}
