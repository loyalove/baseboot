package com.loyalove.baseboot.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by Loyal on 2016/12/3.
 */
@SpringBootApplication
public class Client {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Client.class, args);
        EchoServiceClient bean = run.getBean(EchoServiceClient.class);
        System.out.println(bean.echo("abc"));
    }
}
