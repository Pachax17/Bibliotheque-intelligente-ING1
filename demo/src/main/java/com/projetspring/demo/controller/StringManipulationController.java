package com.projetspring.demo.controller;


import com.projetspring.demo.service.StringManipulationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringManipulationController {

    private final StringManipulationService stringService;

    // Injection du service via le constructeur
    
    public StringManipulationController(StringManipulationService stringService) {
        this.stringService = stringService;
    }

    // Point d'entrée de l'API
    @GetMapping("/api/manipulate")
    public String manipulateName(@RequestParam String prenom) {
        return stringService.processName(prenom);
    }
}

