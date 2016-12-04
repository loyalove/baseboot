package com.loyalove;

import com.loyalove.baseboot.server.EchoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by Loyal on 2016/12/3.
 */
@SpringBootApplication
public class Client {
    @Autowired
    EchoServiceClient echoServiceClient;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Client.class, args);
        EchoServiceClient bean = run.getBean(EchoServiceClient.class);
        System.out.println(bean.echoService.$echo("abccc"));
    }
}
