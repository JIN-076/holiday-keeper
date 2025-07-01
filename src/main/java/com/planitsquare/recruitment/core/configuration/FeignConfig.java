package com.planitsquare.recruitment.core.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.planitsquare.recruitment")
public class FeignConfig {

}
