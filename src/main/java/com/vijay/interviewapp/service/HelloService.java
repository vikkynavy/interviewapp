package com.vijay.interviewapp.service;

import com.vijay.interviewapp.dto.HelloResponse;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public HelloResponse sayHello() {
        return new HelloResponse("Hello World!", "success");
    }
}
