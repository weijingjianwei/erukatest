package com.meikinfo.erukaconsumer.erukaconsumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 描述:
 * RestTemplateConfig
 *
 * @author hongjw
 * @create 2020-02-26 9:54
 */
@Component
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
