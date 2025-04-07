package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ReinitialisationController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/reinitialisation")
    public String afficherFormulaire(@RequestParam("token") String token, Model model) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByToken(token);

        if (utilisateurOpt.isEmpty()) {
            model.addAttribute("message", "❌ Token invalide ou expiré.");
            return "mot-de-passe-oublie-resultat";
        }

        model.addAttribute("token", token);
        return "reinitialisation-mot-de-passe";
    }

    @PostMapping("/reinitialisation")
    public String traiterFormulaire(
            @RequestParam("token") String token,
            @RequestParam("motDePasse") String motDePasse,
            @RequestParam("confirmation") String confirmation,
            Model model
    ) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByToken(token);

        if (utilisateurOpt.isEmpty()) {
            model.addAttribute("message", "❌ Lien expiré ou invalide.");
            return "mot-de-passe-oublie-resultat";
        }

        if (!motDePasse.equals(confirmation)) {
            model.addAttribute("erreur", "❗️ Les mots de passe ne correspondent pas.");
            model.addAttribute("token", token);
            return "reinitialisation-mot-de-passe";
        }

        Utilisateur utilisateur = utilisateurOpt.get();
        utilisateur.setMotDePasse(passwordEncoder.encode(motDePasse));
        utilisateur.setToken(null); // supprime le token
        utilisateurRepository.save(utilisateur);

        model.addAttribute("message", "✅ Mot de passe mis à jour avec succès !");
        return "mot-de-passe-oublie-resultat";
    }
}
