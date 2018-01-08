package com.yonyou.occ.ms.customer.command.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.yonyou.occ.ms.customer.command")
public class FeignConfiguration {

}
