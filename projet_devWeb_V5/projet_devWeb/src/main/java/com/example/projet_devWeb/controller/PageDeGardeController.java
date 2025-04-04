package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.Utilisateur;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageDeGardeController {

    // Méthode pour afficher la page d'accueil de la bibliothèque connectée
    @GetMapping("/")
    public String redirigerVersAccueil() {
        return "redirect:/accueil";
    }

    @GetMapping("/accueil")
    public String afficherAccueil(Model model, Principal principal) {
        model.addAttribute("username", principal != null ? principal.getName() : null);
        return "accueil";
    }

    // Rediriger vers la page de connexion
    @GetMapping("/connexion")
    public String afficherFormulaireConnexion() {
        return "connexion"; // connexion.html
    }

    // Rediriger vers la page d'inscription
    @GetMapping("/inscription")
    public String afficherFormulaireInscription(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "inscription"; // inscription.html
    }

    // Rediriger vers la page profil
    @GetMapping("/profil")
    public String afficherProfil() {
        return "profil"; // profil.html
    }
    
    // Rediriger vers la page administrateur
    @GetMapping("/administrateur")
    public String afficherUtilisateur(Model model) {
        return "administrateur";
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
