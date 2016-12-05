package com.loyalove.baseboot.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loyalove.baseboot.server.EchoService;
import org.springframework.stereotype.Service;

import javax.xml.ws.WebServiceRef;

/**
 * Title: EchoServiceClientImpl.java
 * Description: EchoServiceClientImpl
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-12-05 14:32
 */
@Service
public class EchoServiceClientImpl implements EchoServiceClient {

    @Reference
    private EchoService echoService;

    @Override
    public String echo(String str) {
        return echoService.echo(str);
    }
}
