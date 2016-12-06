package com.loyalove.baseboot.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Loyal on 2016/12/3.
 */
@SpringBootApplication
@MapperScan("com.loyalove.baseboot.dao")
@ComponentScan("com.loyalove.baseboot")
public class Server {

    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder()
                .web(false)
                .sources(Server.class)
                .run(args);
    }
}
