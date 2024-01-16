package com.example.Completable.Future;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiHandler {
    @GetMapping("/health")
    public String healthCheck(){
        return "Up and running";
    }
}
