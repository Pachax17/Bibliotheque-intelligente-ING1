package com.bibliotheque.controller;

import com.bibliotheque.model.Adresse;
import com.bibliotheque.service.ItineraireService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItineraireController {

    private final ItineraireService itineraireService;

    @Autowired
    public ItineraireController(ItineraireService itineraireService) {
        this.itineraireService = itineraireService;
    }

    @GetMapping("/itineraire")
    public String afficherItineraire(Model model) {
        Adresse adresse = itineraireService.getAdresseBibliotheque();
        String mapLink = itineraireService.getLienGoogleMaps();
        String description = itineraireService.getDescription();

        model.addAttribute("adresse", adresse);
        model.addAttribute("mapLink", mapLink);
        model.addAttribute("aPropos", description);

        return "itineraire"; // View : itineraire.html (Thymeleaf)
    }
}
