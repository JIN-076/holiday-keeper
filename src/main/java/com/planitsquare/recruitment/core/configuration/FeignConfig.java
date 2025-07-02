package com.planitsquare.recruitment.core.configuration;

import com.planitsquare.recruitment.exception.feign.FeignErrorDecoder;
import feign.codec.ErrorDecoder;
import feign.codec.ErrorDecoder.Default;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.planitsquare.recruitment")
public class FeignConfig {

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder(new Default());
    }

}
