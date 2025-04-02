package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.services.EmailService;
import com.example.projet_devWeb.services.UtilisateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private EmailService emailService;

    
    
    @Autowired
    private PasswordEncoder passwordEncoder; // ajoute cette ligne

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
            // ✅ Crypte le mot de passe ici
            String motDePasseCrypte = passwordEncoder.encode(utilisateur.getMotDePasse());
            utilisateur.setMotDePasse(motDePasseCrypte);

            // Génère le token + statut non vérifié
            String token = UUID.randomUUID().toString();
            utilisateur.setToken(token);
            utilisateur.setVerifie(false);

            utilisateurService.enregistrer(utilisateur);

            emailService.envoyerEmailConfirmation(utilisateur.getEmail(), utilisateur.getPrenom(), token);

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


    // Vérifie le token de validation
    @GetMapping("/verification")
    public String verifierToken(@RequestParam("token") String token, Model model) {
        Optional<Utilisateur> utilisateurOpt = utilisateurService.findByToken(token);

        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            utilisateurService.validerUtilisateur(utilisateur);
            model.addAttribute("message", "✅ Félicitations ! Votre compte a bien été vérifié.");
        } else {
            model.addAttribute("message", "❌ Ce lien de vérification est invalide ou expiré.");
        }

        return "confirmation"; // confirmation.html
    }

    @GetMapping("/connexion")
    public String afficherFormulaireConnexion() {
        return "connexion";
    }


    // Affiche la liste des utilisateurs
    @GetMapping("/utilisateur")
    public String afficherUtilisateur(Model model) {
        List<Utilisateur> utilisateurs = utilisateurService.getAll();
        model.addAttribute("utilisateur", utilisateurs);
        return "utilisateur";
    }
}
