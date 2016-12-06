package com.loyalove.baseboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by Loyal on 2016/12/3.
 */
@SpringBootApplication
public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Server.class, args);
        logger.info("Dubbo服务启动成功，环境：{}", context.getEnvironment().getActiveProfiles());
    }
}
