package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.DemandeAjoutObjet;
import com.example.projet_devWeb.model.Salle;
import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.model.Objet;
import com.example.projet_devWeb.repository.SalleRepository;
import com.example.projet_devWeb.services.DemandeAjoutObjetService;
import com.example.projet_devWeb.services.UtilisateurService;
import com.example.projet_devWeb.services.ObjetService;
import com.example.projet_devWeb.services.SalleService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Objets")
public class ObjetController {

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private DemandeAjoutObjetService demandeAjoutObjetService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ObjetService objetService;

    @Autowired
    private SalleService salleService;

    // ✅ Formulaire proposition d’objet
    @GetMapping("/proposer")
    public String afficherFormulaireProposition(Model model, Principal principal) {
        model.addAttribute("demandeAjoutObjet", new DemandeAjoutObjet());
        model.addAttribute("salles", salleRepository.findAll());
        return "proposer-objet";
    }

    // ✅ Traitement de la proposition
    @PostMapping("/proposer")
    public String soumettreDemandeObjet(@ModelAttribute DemandeAjoutObjet demande,
                                        @RequestParam("salle") Long salleId,
                                        Principal principal) {
        String email = principal.getName();
        Utilisateur utilisateur = utilisateurService.trouverParEmail(email);
        demande.setUtilisateur(utilisateur);

        // 👇 Associer la salle à partir de l'ID
        Optional<Salle> salle = salleRepository.findById(salleId);
        salle.ifPresent(demande::setSalle);

        demandeAjoutObjetService.soumettreDemande(demande);
        return "redirect:/accueil?demandeEnvoyee";
    }

    // ✅ Affichage + filtrage
    @GetMapping
    public String afficherObjets(@RequestParam(required = false) List<String> type,
                                 @RequestParam(required = false) String salle,
                                 Model model) {
        if (type == null) {
            type = List.of();
        }

        List<Objet> objets = objetService.getObjetsFiltres(type, salle);
        List<String> types = List.of("Lumiere", "Temperature", "Visuel");

        List<String> nomsSalles = List.of(
                "Salle Informatique",
                "Salle Lecture",
                "Salle Réunion",
                "Salle Archives",
                "Salle Détente"
        );

        model.addAttribute("objets", objets);
        model.addAttribute("types", types);
        model.addAttribute("salles", nomsSalles);
        model.addAttribute("type", type);
        model.addAttribute("salle", salle);

        return "Objets";
    }

    // ✅ Détails d’un objet
    @GetMapping("/{id}")
    public String afficherDetailsObjet(@PathVariable Long id, Model model, Principal principal) {
        Optional<Objet> optionalObjet = objetService.findById(id);
        if (optionalObjet.isPresent()) {
            Objet objet = optionalObjet.get();
            model.addAttribute("objet", objet);

            if (principal != null) {
                String email = principal.getName();
                Utilisateur utilisateur = utilisateurService.trouverParEmail(email);
                model.addAttribute("utilisateurConnecte", utilisateur);
            }

            return "objet-detail";
        } else {
            return "redirect:/Objets?notfound";
        }
    }

    // ✅ Activation / désactivation
    @PostMapping("/{id}/etat")
    public String changerEtatObjet(@PathVariable Long id,
                                   @RequestParam("actif") boolean actif,
                                   Principal principal) {
        Utilisateur utilisateur = utilisateurService.trouverParEmail(principal.getName());
        objetService.changerEtatObjet(id, actif, utilisateur);
        return "redirect:/Objets/" + id;
    }

    // ✅ Formulaire de modification
    @GetMapping("/{id}/modifier")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        Optional<Objet> optionalObjet = objetService.findById(id);
        if (optionalObjet.isPresent()) {
            model.addAttribute("objet", optionalObjet.get());
            model.addAttribute("salles", salleService.findAll());
            return "objet-modifier";
        } else {
            return "redirect:/Objets?notfound";
        }
    }

    // ✅ Traitement modification
    @PostMapping("/{id}/modifier")
    public String modifierObjet(@PathVariable Long id,
                                @ModelAttribute("objet") Objet objetModifie) {
        objetService.mettreAJourObjet(id, objetModifie);
        return "redirect:/Objets/" + id + "?modifie";
    }
}
