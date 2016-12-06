package com.loyalove.baseboot.app.controller;

import com.loyalove.baseboot.common.model.Result;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title: HomeController.java
 * Description: HomeController
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-11-29 16:03
 */
@RestController
public class HomeController extends BaseController {

    @RequestMapping(value = {"", "/"})
    public Result home(Model model) {
        return Result.getResultSuccess("欢迎");
    }

}
