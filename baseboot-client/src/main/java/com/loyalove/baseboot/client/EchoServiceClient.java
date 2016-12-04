package com.loyalove.baseboot.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loyalove.baseboot.server.EchoService;
import org.springframework.stereotype.Service;

/**
 * Created by Loyal on 2016/12/3.
 */

@Service
public class EchoServiceClient {

    @Reference
    private EchoService echoService;

    public String echo(String str) {
        return echoService.echo(str);
    }
}