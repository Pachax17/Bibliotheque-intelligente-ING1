package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.DemandeAjoutObjet;
import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.model.Objet;
import com.example.projet_devWeb.model.Salle;
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

    // Affiche le formulaire pour proposer un objet
    @GetMapping("/proposer")
    public String afficherFormulaireProposition(Model model, Principal principal) {
        model.addAttribute("demandeAjoutObjet", new DemandeAjoutObjet());
        model.addAttribute("salles", salleRepository.findAll());
        return "proposer-objet";
    }

    // Soumet une demande de proposition d'objet
    @PostMapping("/proposer")
    public String soumettreDemandeObjet(@ModelAttribute("demandeAjoutObjet") DemandeAjoutObjet demande,
                                        Principal principal) {
        String email = principal.getName();
        Utilisateur utilisateur = utilisateurService.trouverParEmail(email);
        demande.setUtilisateur(utilisateur);
        demandeAjoutObjetService.soumettreDemande(demande);
        return "redirect:/accueil?demandeEnvoyee";
    }

    // Affiche la liste des objets filtrés
    @GetMapping
    public String afficherObjets(@RequestParam(required = false) List<String> type,
                                 @RequestParam(required = false) String salle,
                                 Model model) {
        if (type == null) {
            type = List.of();
        }

        List<Objet> objets = objetService.getObjetsFiltres(type, salle);
        List<String> types = List.of("Lumiere", "Temperature", "Visuel");
        List<Salle> salles = salleService.findAll();

        model.addAttribute("objets", objets);
        model.addAttribute("types", types);
        model.addAttribute("salles", salles);
        model.addAttribute("type", type);
        model.addAttribute("salle", salle);

        return "Objets";
    }

    // Détail d’un objet
    @GetMapping("/{id}")
    public String afficherDetailsObjet(@PathVariable Long id, Model model) {
        Optional<Objet> optionalObjet = objetService.findById(id);
        if (optionalObjet.isPresent()) {
            model.addAttribute("objet", optionalObjet.get());
            return "objet-detail";
        } else {
            return "redirect:/Objets?notfound";
        }
    }

    // Changement d’état actif/inactif
    @PostMapping("/{id}/etat")
    public String changerEtatObjet(@PathVariable Long id,
                                   @RequestParam("actif") boolean actif,
                                   Principal principal) {
        objetService.changerEtatObjet(id, actif);
        return "redirect:/Objets/" + id;
    }

    // Formulaire de modification
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

    // Traitement de la modification
    @PostMapping("/{id}/modifier")
    public String modifierObjet(@PathVariable Long id,
                                @ModelAttribute("objet") Objet objetModifie) {
        objetService.mettreAJourObjet(id, objetModifie);
        return "redirect:/Objets/" + id + "?modifie";
    }
}
