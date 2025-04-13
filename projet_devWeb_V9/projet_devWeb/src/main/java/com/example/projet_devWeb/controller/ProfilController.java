package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.ActiviteTracker;
import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class ProfilController {

    @Autowired
    private HistoriqueActivationService historiqueService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ActiviteTrackerService activiteTrackerService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DemandeRoleService demandeRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profil")
    public String afficherProfil(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Utilisateur utilisateur = utilisateurService.trouverParEmail(email);

            ActiviteTracker tracker = activiteTrackerService.findByUtilisateur(utilisateur);
            int connexions = tracker != null ? tracker.getConnexionsAujourdHui() : 0;
            int activations = tracker != null ? tracker.getActivationsAujourdHui() : 0;

            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("points", utilisateur.getPoints());
            model.addAttribute("connexionsAujourdHui", connexions);
            model.addAttribute("activationsAujourdHui", activations);
            model.addAttribute("peutDemanderRoleAvance", utilisateur.getRole().equals(Utilisateur.Role.SIMPLE) && utilisateur.getPoints() >= 100);
            return "profil";
        }
        return "redirect:/connexion";
    }

    @PostMapping("/demander-role-avance")
    public String demanderRoleAvance(Principal principal) {
        if (principal != null) {
            Utilisateur utilisateur = utilisateurService.trouverParEmail(principal.getName());
            if (utilisateur.getRole().equals(Utilisateur.Role.SIMPLE) && utilisateur.getPoints() >= 100) {
                demandeRoleService.enregistrerDemande(utilisateur);
                emailService.envoyerEmailAdmin(
                        "admin@projet.fr",
                        "Nouvelle demande de rôle avancé",
                        "L'utilisateur " + utilisateur.getPrenom() + " " + utilisateur.getNom() +
                                " (" + utilisateur.getEmail() + ") a demandé à devenir AVANCÉ avec " + utilisateur.getPoints() + " points."
                );
            }
        }
        return "redirect:/profil?demandeEnvoyee";
    }

    @GetMapping("/profil/historique")
    public String afficherHistoriqueUtilisateur(Model model, Principal principal) {
        if (principal != null) {
            Utilisateur utilisateur = utilisateurService.trouverParEmail(principal.getName());
            model.addAttribute("historique", historiqueService.findByUtilisateur(utilisateur));
            return "profil-historique";
        }
        return "redirect:/connexion";
    }

    // 🔄 Modifier profil
    @PostMapping("/profil/modifier")
    public String modifierProfil(Principal principal,
                                 @RequestParam String prenom,
                                 @RequestParam String nom,
                                 @RequestParam String email) {
        if (principal != null) {
            Utilisateur utilisateur = utilisateurService.trouverParEmail(principal.getName());

            utilisateur.setPrenom(prenom);
            utilisateur.setNom(nom);
            utilisateur.setEmail(email);

            utilisateurService.sauvegarder(utilisateur);

            return "redirect:/profil?modif=success";
        }
        return "redirect:/connexion";
    }

    // 🔐 Changer le mot de passe
    @PostMapping("/profil/mot-de-passe")
    public String changerMotDePasse(Principal principal,
                                    @RequestParam String oldPassword,
                                    @RequestParam String newPassword,
                                    Model model) {
        if (principal != null) {
            Utilisateur utilisateur = utilisateurService.trouverParEmail(principal.getName());

            if (passwordEncoder.matches(oldPassword, utilisateur.getMotDePasse())) {
                utilisateur.setMotDePasse(passwordEncoder.encode(newPassword));
                utilisateurService.sauvegarder(utilisateur);
                return "redirect:/profil?mdpModifie";
            } else {
                return "redirect:/profil?erreurMotDePasse";
            }
        }
        return "redirect:/connexion";
    }
}
