package com.yonyou.occ.ms.order.command.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.yonyou.occ.ms.order.command")
public class FeignConfiguration {

}
