package com.meikinfo.erukaconsumer.erukaconsumer.controller;

//import com.meikinfo.erukaconsumer.erukaconsumer.service.GetNameRemote;
import com.meikinfo.erukaconsumer.erukaconsumer.service.GetNameRemote;
import com.netflix.discovery.converters.Auto;
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
//    @Autowired
//    private GetNameRemote getNameRemote;


    @GetMapping(value = "/consumer")
    public String consumertest1(){
        String template = restTemplate.getForObject("http://ERUKAPROVIDE/provide/id111", String.class);
//        String template = restTemplate.getForObject("http://localhost:8000/erukaprovider/provide/id111", String.class);
        return template;
//        String provideMessage = getNameRemote.provideMessage("dsaa");
//        return provideMessage;
    }
}
