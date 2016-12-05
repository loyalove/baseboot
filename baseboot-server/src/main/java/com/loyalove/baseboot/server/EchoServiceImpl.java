package com.loyalove.baseboot.server;


import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Loyal on 2016/12/3.
 */
@Service
public class EchoServiceImpl implements EchoService {
    private static final Logger logger = LoggerFactory.getLogger(EchoServiceImpl.class);

    @Override
    public String echo(String str) {
        logger.info("来自客户端的信息：{}", str);
        return String.format("来自服务端的信息：Hello, %s", str);
    }
}
