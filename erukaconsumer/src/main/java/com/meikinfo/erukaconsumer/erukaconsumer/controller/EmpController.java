package com.meikinfo.erukaconsumer.erukaconsumer.controller;

import cn.hutool.core.lang.Dict;
import com.meikinfo.erukaconsumer.erukaconsumer.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述:
 * empcontroller
 *
 * @author hongjw
 * @create 2020-02-29 14:50
 */
@RestController
public class EmpController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/employee/basic/emp")
    public List<Person> loadEmps(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Long politicid,Long posid){
//        Person[] people = restTemplate.getForObject("http://ERUKAPROVIDE/employee/basic/emp?page=" + page + "&size=" + size+"", Person[].class);
//        return Arrays.asList(people);
        Dict params = Dict.create()
                .set("page", page)
                .set("size", size)
                .set("politicid", politicid)
                .set("posid", posid);
        Person[] peoples = restTemplate.
                getForObject("http://ERUKAPROVIDE/employee/basic/emp?page={page}&size={size}&politicid={politicid}&posid={posid}", Person[].class, params);
        return Arrays.asList(peoples);
    }

    @GetMapping(value = "/employee/basic/basicdata")
    public Dict loadBaseData(){
        Dict baseDict = restTemplate.getForObject("http://ERUKAPROVIDE/employee/basic/basicdata", Dict.class);
        return baseDict;
    }

}
