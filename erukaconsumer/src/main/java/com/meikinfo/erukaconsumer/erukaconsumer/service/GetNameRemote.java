//package com.meikinfo.erukaconsumer.erukaconsumer.service;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
///**
// * 描述:
// * GetNameRemote
// *
// * @author hongjw
// * @create 2020-02-26 11:18
// */
//
//@FeignClient(value = "ERUKAPROVIDE")
//public interface GetNameRemote {
//    @RequestMapping(value = "/provide/{id}",method = RequestMethod.GET)
//    public String provideMessage(@PathVariable("id") String id);
//
//}
