package com.meikinfo.erukaconsumer.erukaconsumer.controller;

//import com.meikinfo.erukaconsumer.erukaconsumer.service.GetNameRemote;
import com.meikinfo.erukaconsumer.erukaconsumer.domain.Person;
import com.netflix.discovery.converters.Auto;
import org.omg.CORBA.SetOverrideType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    @RequestMapping(value = "/employee/basic/emp")
    public void loadEmps(@RequestParam Integer page, @RequestParam Integer size){
        System.out.println("-------------");
    }

//    public interface GetNameRemote {
//        @GetMapping("/provide/{id}")
//        public String provideMessage(@PathVariable("id") String id);
//
//    }
}
