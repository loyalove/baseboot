package com.loyalove.baseboot.web.controller;

import com.loyalove.baseboot.common.model.Result;
import com.loyalove.baseboot.web.config.shiro.NoActiveAccountException;
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
 * Title: LoginController.java
 * Description: LoginController
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-11-29 16:03
 */
@Controller
public class LoginController extends BaseController {

    private static final String BEGIN_MARKER = "对用户[{}]进行登录验证..开始验证";
    private static final String SUCCESS_MARKER = "对用户[{}]进行登录验证..验证成功,{}";
    private static final String FAIL_MARKER = "对用户[{}]进行登录验证..验证失败,{}";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin(Model model) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(UserPO userPO) {
        Subject currUser = SecurityUtils.getSubject();
        String username = userPO.getUsername();
        UsernamePasswordToken token = new UsernamePasswordToken(username, userPO.getPassword());
        Result result;
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info(BEGIN_MARKER, username);
            currUser.login(token);
            result = Result.getResultSuccess("登录成功", username);
            logger.info(SUCCESS_MARKER, username, result.getMessage());
            return result;
        } catch (UnknownAccountException e) {
            result = Result.getResultFail(e.getMessage());
        } catch (LockedAccountException e) {
            result = Result.getResultFail(e.getMessage());
        } catch (NoActiveAccountException e) {
            result = Result.getResultFail(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            result = Result.getResultFail("密码不正确");
        } catch (ExcessiveAttemptsException e) {
            result = Result.getResultFail("用户名或密码错误次数过多");
        } catch (AuthenticationException e) {
            result = Result.getResultFail("用户名或密码不正确");
            logger.error("堆栈信息：{}", e);
        }

        logger.info(FAIL_MARKER, username, result.getMessage());

        return result;
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public Object logout() {
        SecurityUtils.getSubject().logout();
        return Result.getResultSuccess("安全退出");
    }
}
