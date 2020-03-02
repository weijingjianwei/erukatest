package com.meikinfo.erukaprovide.erukaprovide.controller;

import com.meikinfo.erukaprovide.erukaprovide.service.ProvideServicetest1;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 描述:
 * ProvideControllertest1
 *
 * @author hongjw
 * @create 2020-02-24 13:19
 */
@RestController
public class ProvideControllertest1 {

    @Autowired
    private ProvideServicetest1 provideServicetest1;

    @RequestMapping(value = "/provide/{id}",method = RequestMethod.GET)
    public String provideMessage(@PathVariable("id") String id){
        return provideServicetest1.provideMessage(id);
    }
}
