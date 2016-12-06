package com.loyalove.baseboot.dao.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;

/**
 * Title: MybatisConfig.java
 * Description: MybatisConfig
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-12-06 14:25
 */
@SpringBootConfiguration
@MapperScan("com.loyalove.baseboot.dao")
public class MybatisConfig {
}
