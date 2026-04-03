package com.vijay.interviewapp.dto;

import com.vijay.interviewapp.service.HelloService;

public class HelloResponse {

    private final String message;
    private final String status;

    public HelloResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
