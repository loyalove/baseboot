package com.loyalove.baseboot.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loyalove.baseboot.server.EchoService;
import org.springframework.stereotype.Service;

/**
 * Created by Loyal on 2016/12/3.
 */
public interface EchoServiceClient {
    String echo(String str);
}