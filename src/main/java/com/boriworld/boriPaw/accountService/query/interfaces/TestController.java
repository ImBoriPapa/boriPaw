package com.boriworld.boriPaw.accountService.query.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String greeting() {

        return "Hi !!!";
    }
}