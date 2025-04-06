package com.example.projet_devWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/acces-refuse")
    public String accesRefuse() {
        return "acces-refuse"; // ✅ retourne bien acces-refuse.html
    }
}
