//package com.meikinfo.erukaprovide.erukaprovide.config;
//
//import feign.form.spring.SpringFormEncoder;
//import org.springframework.beans.factory.ObjectFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.cloud.netflix.feign.support.SpringEncoder;
//import org.springframework.context.annotation.Bean;
//
//
///**
// * 描述:
// * 服务方文件配置类
// *
// * @author hongjw
// * @create 2020-02-25 10:34
// */
//public class MultipartSupportConfig {
//    @Autowired
//    private ObjectFactory<HttpMessageConverters> messageConverters;
//
//    @Bean
//    public Encoder feignFormEncoder() {
//        return new SpringFormEncoder(new SpringEncoder(messageConverters));
//    }
//
//
//}
