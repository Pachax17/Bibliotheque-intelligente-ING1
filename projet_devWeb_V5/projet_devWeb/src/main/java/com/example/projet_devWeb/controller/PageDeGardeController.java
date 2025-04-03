package com.example.projet_devWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageDeGardeController {

    // Méthode pour afficher la page d'accueil de la bibliothèque connectée
    @GetMapping("/accueil")
    public String afficherPageGarde() {
        return "PageGarde";  // Nom du template Thymeleaf, ici "PageGarde.html"
    }

    // Rediriger vers la page de connexion
    @GetMapping("/login")
    public String afficherPageConnexion() {
        return "connexion";  // Nom du template pour la page de connexion
    }

    // Rediriger vers la page d'inscription
    @GetMapping("/signup")
    public String afficherPageInscription() {
        return "inscription";  // Nom du template pour la page d'inscription
    }

    // Rediriger vers la page agenda
    @GetMapping("/agenda")
    public String afficherPageAgenda() {
        return "agenda";  // Nom du template pour la page agenda
    }

    // Rediriger vers la page de contact
    @GetMapping("/contact")
    public String afficherPageContact() {
        return "contact";  // Nom du template pour la page de contact
    }

    // Rediriger vers la page itinéraire
    @GetMapping("/itineraire")
    public String afficherPageItineraire() {
        return "itineraire";  // Nom du template pour la page itinéraire
    }
    @GetMapping("/Objets")
    public String afficherPageObjets() {
        return "Objets";  // Nom du template pour la page itinéraire
    }
}
