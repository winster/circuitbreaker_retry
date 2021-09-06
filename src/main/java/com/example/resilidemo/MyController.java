package com.example.resilidemo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MyController {

    private final MyService myService;

    @GetMapping("/api")
    public String api1() {
        return myService.backendA();
    }
}
