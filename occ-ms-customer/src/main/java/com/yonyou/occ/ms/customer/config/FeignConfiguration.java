package com.yonyou.occ.ms.customer.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.yonyou.occ.ms.customer")
public class FeignConfiguration {

}
