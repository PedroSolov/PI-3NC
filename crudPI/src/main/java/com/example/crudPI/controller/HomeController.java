package com.example.crudPI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // isso vai procurar o arquivo src/main/resources/templates/index.html
    }
}
