package com.loyalove.baseboot.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by Loyal on 2016/12/3.
 */
@SpringBootApplication
@MapperScan("com.loyalove.baseboot")
public class Server {

    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder()
                .web(false)
                .sources(Server.class)
                .run(args);
    }
}
