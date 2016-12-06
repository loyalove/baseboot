package com.loyalove.baseboot.web;

import com.loyalove.baseboot.common.model.Pager;
import com.loyalove.baseboot.pojo.UserPO;
import com.loyalove.baseboot.web.client.auth.UserServiceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * Title: WebApiStart.java
 * Description: WebApiStart
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-11-28 17:21
 */
@SpringBootApplication
public class WebStart {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .sources(WebStart.class)
                .web(false)
                .run(args);
        UserServiceClient bean = context.getBean(UserServiceClient.class);
        List<UserPO> userPOs = bean.queryUsers(new Pager());
    }
}
