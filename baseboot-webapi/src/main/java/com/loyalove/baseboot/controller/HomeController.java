package com.loyalove.baseboot.controller;

import com.loyalove.baseboot.common.model.Result;
import com.loyalove.baseboot.config.shiro.NoActiveAccountException;
import com.loyalove.baseboot.pojo.UserPO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = {"", "/"})
    public String home(Model model) {
        return "home";
    }

}
