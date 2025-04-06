package com.example.bibliotheque.controller;

import com.example.bibliotheque.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping("/agenda")
    public String afficherAgenda(Model model) {
        model.addAttribute("periodeScolaire", agendaService.getHorairesPeriodeScolaire());
        model.addAttribute("vacances", agendaService.getHorairesVacances());
        return "agenda";
    }
}
