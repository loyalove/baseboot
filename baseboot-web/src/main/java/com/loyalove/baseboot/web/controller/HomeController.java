package com.loyalove.baseboot.web.controller;

import com.loyalove.baseboot.common.model.Pager;
import com.loyalove.baseboot.pojo.UserPO;
import com.loyalove.baseboot.web.client.auth.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Title: HomeController.java
 * Description: HomeController
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-11-29 16:03
 */
@Controller
public class HomeController extends BaseController {

    @Autowired
    UserServiceClient userServiceClient;

    @RequestMapping(value = {"", "/"})
    public String home(Model model) {
        List<UserPO> userPOs = userServiceClient.queryUsers(new Pager());
        model.addAttribute("users",userPOs);
        return "home";
    }

}
