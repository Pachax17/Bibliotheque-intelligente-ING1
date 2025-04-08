package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.DemandeRole;
import com.example.projet_devWeb.services.DemandeRoleService;
import com.example.projet_devWeb.services.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/administrateur/traiter-demande-avance")
public class AdminDemandeAvanceController {

    @Autowired
    private DemandeRoleService demandeRoleService;

    @Autowired
    private EmailService emailService;

    // 🔍 Affiche la liste des demandes de rôle Avancé non encore traitées
    @GetMapping
    public String afficherDemandesAvance(Model model) {
        List<DemandeRole> demandesAvance = demandeRoleService.getDemandesNonTraitees();
        model.addAttribute("demandesAvance", demandesAvance);
        return "traiter-demande-avance"; // fichier dans /templates/
    }

    // ✅ Accepter une demande
    @PostMapping("/{id}/accepter")
    public String accepterDemande(@PathVariable Long id) {
        DemandeRole demande = demandeRoleService.getById(id);
        if (demande != null && !demande.isTraitee()) {
            demandeRoleService.validerDemande(id); // mise à jour de la demande et promotion de l'utilisateur

            // 📧 Notification email à l'utilisateur
            String message = "Bonjour " + demande.getUtilisateur().getPrenom() + ",\n\n" +
                    "Votre demande pour devenir utilisateur Avancé a été acceptée !\n\n" +
                    "🎉 Félicitations et à bientôt sur Ma Bibliothèque.";
            emailService.envoyerEmail(
                    demande.getUtilisateur().getEmail(),
                    "🎉 Rôle Avancé accepté",
                    message
            );
        }

        return "redirect:/administrateur/traiter-demande-avance";
    }

    // ❌ Refuser une demande
    @PostMapping("/{id}/refuser")
    public String refuserDemande(@PathVariable Long id) {
        DemandeRole demande = demandeRoleService.getById(id);
        if (demande != null && !demande.isTraitee()) {
            demandeRoleService.refuserDemande(id); // marque la demande comme traitée

            // 📧 Notification email à l'utilisateur
            String message = "Bonjour " + demande.getUtilisateur().getPrenom() + ",\n\n" +
                    "Votre demande pour devenir utilisateur Avancé a été refusée.\n" +
                    "N'hésitez pas à réessayer plus tard !";
            emailService.envoyerEmail(
                    demande.getUtilisateur().getEmail(),
                    "⛔ Demande refusée",
                    message
            );
        }

        return "redirect:/administrateur/traiter-demande-avance";
    }
}
