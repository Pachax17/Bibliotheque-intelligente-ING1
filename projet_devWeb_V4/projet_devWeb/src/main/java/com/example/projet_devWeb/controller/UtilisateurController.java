package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.services.EmailService;
import com.example.projet_devWeb.services.UtilisateurService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private EmailService emailService;

    // Affiche la page d'inscription
    @GetMapping("/inscription")
    public String afficherFormulaireInscription(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "inscription";
    }

    // Enregistre un nouvel utilisateur
    @PostMapping("/inscription")
    public String enregistrerUtilisateur(@ModelAttribute("utilisateur") Utilisateur utilisateur, Model model) {
        try {
            utilisateurService.enregistrer(utilisateur);

            emailService.envoyerEmailConfirmation(utilisateur.getEmail(), utilisateur.getPrenom());

            model.addAttribute("success", true);
            return "inscription";

        } catch (DataIntegrityViolationException e) {
            model.addAttribute("erreur", "Cet email est déjà utilisé !");
            return "inscription";
        } catch (MailException e) {
            model.addAttribute("erreur", "Inscription OK mais l'envoi d'email a échoué.");
            return "inscription";
        }
    }



    // Affiche la liste des utilisateurs
    @GetMapping("/utilisateur")
    public String afficherUtilisateur(Model model) {
        List<Utilisateur> utilisateurs = utilisateurService.getAll();
        model.addAttribute("utilisateur", utilisateurs);
        return "utilisateur";
    }
}
