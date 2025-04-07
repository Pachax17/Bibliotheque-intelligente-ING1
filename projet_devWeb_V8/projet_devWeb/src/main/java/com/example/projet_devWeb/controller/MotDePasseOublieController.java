package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.repository.UtilisateurRepository;
import com.example.projet_devWeb.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
public class MotDePasseOublieController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/mot-de-passe-oublie")
    public String afficherFormulaire() {
        return "mot-de-passe-oublie";
    }

    @PostMapping("/mot-de-passe-oublie")
    public String traiterFormulaire(@RequestParam("email") String email, Model model) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);

        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();

            // Génère un token de reset
            String token = UUID.randomUUID().toString();
            utilisateur.setToken(token);
            utilisateurRepository.save(utilisateur);

            // Envoie l'email de réinitialisation
            emailService.envoyerLienDeReset(utilisateur);
            model.addAttribute("message", "📧 Un lien de réinitialisation vous a été envoyé.");
        } else {
            model.addAttribute("message", "❌ Aucun utilisateur trouvé avec cet email.");
        }

        return "mot-de-passe-oublie-resultat";
    }
}
