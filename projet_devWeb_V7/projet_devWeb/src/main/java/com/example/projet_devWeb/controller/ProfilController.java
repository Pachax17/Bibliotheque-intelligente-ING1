package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.HistoriqueActivation;
import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.services.HistoriqueActivationService;
import com.example.projet_devWeb.services.UtilisateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ProfilController {

    @Autowired
    private HistoriqueActivationService historiqueService;

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/profil/historique")
    public String afficherHistoriqueUtilisateur(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Utilisateur utilisateur = utilisateurService.trouverParEmail(email);
            List<HistoriqueActivation> historique = historiqueService.findByUtilisateur(utilisateur);

            model.addAttribute("historique", historique);
            return "profil-historique";
        }

        return "redirect:/connexion";
    }
}
