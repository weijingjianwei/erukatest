package com.meikinfo.erukaconsumer.erukaconsumer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 首页Controller
 * </p>
 */
@RestController
public class IndexController {
    @GetMapping("/test")
    public String index() {
        System.out.println("===============");
        return "----";
    }
}
