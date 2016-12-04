package com.loyalove.baseboot.server;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.service.EchoService;
import org.springframework.stereotype.Component;

/**
 * Created by Loyal on 2016/12/3.
 */

@Component
public class EchoServiceClient {

    @Reference(version = "1.0.0")
    public EchoService echoService;
}