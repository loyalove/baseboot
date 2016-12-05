package com.loyalove.baseboot.client;

import com.google.gson.Gson;
import com.loyalove.baseboot.pojo.UserPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * Created by Loyal on 2016/12/3.
 */
@SpringBootApplication
public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Client.class, args);
        UserServiceClient bean = run.getBean(UserServiceClient.class);
        List<UserPO> userPOs = bean.queryAll();
        logger.info("获得数据：{}", new Gson().toJson(userPOs));
    }
}
