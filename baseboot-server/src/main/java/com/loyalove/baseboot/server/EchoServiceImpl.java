package com.loyalove.baseboot.server;


import com.alibaba.dubbo.config.annotation.Service;

/**
 * Created by Loyal on 2016/12/3.
 */
@Service
public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String str) {
        System.out.println(str);
        return "OK";
    }
}
