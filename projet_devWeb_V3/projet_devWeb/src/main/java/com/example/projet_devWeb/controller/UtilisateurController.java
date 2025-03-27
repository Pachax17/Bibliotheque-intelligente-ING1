package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    // Affiche le formulaire d'inscription (GET)
    @GetMapping("/inscription")
    public String afficherFormulaireInscription(Model model) {
        model.addAttribute("utilisateur", new Utilisateur()); // Objet vide pour le binding Thymeleaf
        return "inscription"; // => templates/inscription.html
    }

    // Traite le formulaire d'inscription (POST)
    @PostMapping("/inscription")
    public String enregistrerUtilisateur(@ModelAttribute Utilisateur utilisateur, Model model) {
    try {
            utilisateurService.enregistrer(utilisateur);
            return "redirect:/utilisateur";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("erreur", "Cet email est déjà utilisé !");
            return "inscription"; // Reviens à la page d'inscription avec le message d'erreur
        }
    }


    // Affiche la liste des utilisateurs enregistrés
    @GetMapping("/utilisateur")
    public String afficherUtilisateur(Model model) {
        List<Utilisateur> liste = utilisateurService.getAll();
        model.addAttribute("utilisateur", liste);
        return "utilisateur"; // => templates/utilisateurs.html
    }
}
