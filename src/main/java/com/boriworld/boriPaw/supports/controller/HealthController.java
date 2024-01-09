package com.boriworld.boriPaw.supports.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/greeting")
    public String greeting() {

        return "Hi~";
    }
}
