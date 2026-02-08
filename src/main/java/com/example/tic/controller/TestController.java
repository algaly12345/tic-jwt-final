package com.example.tic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/secure/ping")
    public String ping() {
        return "pong (secured)";
    }
}
