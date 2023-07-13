package com.example.test_rabbitmq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：liujl
 * @date ：Created 2023/7/12 17:32
 * @description：
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
