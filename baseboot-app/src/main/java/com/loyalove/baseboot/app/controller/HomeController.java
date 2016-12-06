package com.loyalove.baseboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
