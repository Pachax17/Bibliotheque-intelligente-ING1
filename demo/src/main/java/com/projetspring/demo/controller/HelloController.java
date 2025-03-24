package com.projetspring.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String homePage() {
        return "Bienvenue sur mon application Spring Boot !";
    }

    @GetMapping("/bonjour/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
    }
}
