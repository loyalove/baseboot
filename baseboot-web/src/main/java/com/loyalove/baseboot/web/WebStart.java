package com.loyalove.baseboot.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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

    private static final Logger logger = LoggerFactory.getLogger(WebStart.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WebStart.class, args);
        logger.info("Web服务启动成功，{}", context.getEnvironment().getActiveProfiles());
    }
}
