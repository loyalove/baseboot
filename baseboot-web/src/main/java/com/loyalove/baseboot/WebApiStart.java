package com.loyalove.baseboot;

import org.mybatis.spring.annotation.MapperScan;
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
public class WebApiStart {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WebApiStart.class);
    }
}
