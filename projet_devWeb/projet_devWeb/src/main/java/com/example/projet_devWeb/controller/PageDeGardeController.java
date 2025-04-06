package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.Utilisateur;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageDeGardeController {

    @GetMapping("/")
    public String redirigerVersAccueil() {
        return "redirect:/accueil";
    }

    @GetMapping("/accueil")
    public String afficherAccueil(Model model, Principal principal) {
        model.addAttribute("username", principal != null ? principal.getName() : null);
        return "accueil";
    }

    @GetMapping("/connexion")
    public String afficherFormulaireConnexion() {
        return "connexion"; // connexion.html
    }

    @GetMapping("/inscription")
    public String afficherFormulaireInscription(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "inscription"; // inscription.html
    }

    @GetMapping("/profil")
    public String afficherProfil() {
        return "profil"; // profil.html
    }

    @GetMapping("/administrateur")
    public String afficherUtilisateur(Model model) {
        return "administrateur";
    }

    @GetMapping("/agenda")
    public String afficherPageAgenda() {
        return "agenda"; // agenda.html
    }

    @GetMapping("/contact")
    public String afficherPageContact() {
        return "contact"; // contact.html
    }

    @GetMapping("/itineraire")
    public String afficherPageItineraire() {
        return "itineraire"; // itineraire.html
    }

    @GetMapping("/accueil/Objets")
    public String afficherPageObjets() {
        return "Objets"; // ou une autre vue si tu veux différencier
    }


}
