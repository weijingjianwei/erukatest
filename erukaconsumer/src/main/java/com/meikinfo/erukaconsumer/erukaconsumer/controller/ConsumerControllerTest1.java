package com.meikinfo.erukaconsumer.erukaconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 描述:
 * controllertest1
 *
 * @author hongjw
 * @create 2020-02-24 13:18
 */
@RestController
public class ConsumerControllerTest1 {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer")
    public String consumertest1(){
        String template = restTemplate.getForObject("http://localhost:8000/provide/id111", String.class);
        return template;
    }
}
