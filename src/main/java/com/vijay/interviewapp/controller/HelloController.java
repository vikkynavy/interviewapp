package com.vijay.interviewapp.controller;

import com.vijay.interviewapp.dto.HelloResponse;
import com.vijay.interviewapp.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }


    @GetMapping("/hello")
    public HelloResponse hello() {
        return helloService.sayHello();
    }


}
