package com.meikinfo.erukaconsumer.erukaconsumer.service;

import com.meikinfo.erukaconsumer.erukaconsumer.domain.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 描述:
 * empservice
 *
 * @author hongjw
 * @create 2020-02-29 14:52
 */
@FeignClient(value = "ERUKAPROVIDE")
public interface EmpServiceRemote {

    @GetMapping(value = "/employee/basic/emp")
    public List<Person> loadEmps(@RequestParam Integer page, @RequestParam Integer size);

//    public interface GetNameRemote {
//        @GetMapping("/provide/{id}")
//        public String provideMessage(@PathVariable("id") String id);
//
//    }
}
