package com.boriworld.boriPaw.supports.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentController {

    @GetMapping("/document")
    public String mapping() {

        return "docs/index.html";
    }
}
