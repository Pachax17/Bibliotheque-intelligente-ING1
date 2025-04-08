package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.DemandeAjoutObjet;
import com.example.projet_devWeb.model.Objet;
import com.example.projet_devWeb.model.Salle;
import com.example.projet_devWeb.services.DemandeAjoutObjetService;
import com.example.projet_devWeb.services.ObjetService;
import com.example.projet_devWeb.services.SalleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/administrateur")
public class AdminObjetController {

    @Autowired
    private DemandeAjoutObjetService demandeService;

    @Autowired
    private ObjetService objetService;

    @Autowired
    private SalleService salleService;


    // ✅ Liste des demandes d’ajout d’objets
    @GetMapping("/demandes-objets")
    public String afficherDemandesAjoutObjet(Model model) {
        List<DemandeAjoutObjet> demandes = demandeService.getDemandesEnAttente();
        model.addAttribute("demandes", demandes);
        return "demandes-objets";
    }

    // ✅ Accepter une demande
    @PostMapping("/demandes-objets/{id}/accepter")
    public String accepterDemandeAjoutObjet(@PathVariable Long id) {
        demandeService.accepterDemande(id);
        return "redirect:/administrateur/demandes-objets";
    }

    // ✅ Refuser une demande
    @PostMapping("/demandes-objets/{id}/refuser")
    public String refuserDemandeAjoutObjet(@PathVariable Long id) {
        demandeService.refuserDemande(id);
        return "redirect:/administrateur/demandes-objets";
    }

    // ✅ Liste des objets existants
    @GetMapping("/GestionObjet")
    public String afficherPageGestionObjets(Model model) {
        model.addAttribute("objets", objetService.findAll());
        return "GestionObjet";
    }

    // ✅ Supprimer un objet
    @PostMapping("/objet/{id}/supprimer")
    public String supprimerObjet(@PathVariable Long id) {
        objetService.supprimerObjet(id);
        return "redirect:/administrateur/GestionObjet?supprime";
    }

    // ✅ Afficher le formulaire de modification
    @GetMapping("/objet/{id}/modifier")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        return objetService.findById(id).map(objet -> {
            model.addAttribute("objet", objet);
            model.addAttribute("salles", salleService.findAll());
            return "modifier-objet";
        }).orElse("redirect:/administrateur/GestionObjet?notfound");
    }

    // ✅ Modifier un objet
    @PostMapping("/objet/{id}/modifier")
    public String modifierObjet(@PathVariable Long id, @ModelAttribute("objet") Objet objetModifie) {
        objetService.mettreAJourObjet(id, objetModifie);
        return "redirect:/administrateur/GestionObjet?modifie";
    }

    // ✅ Formulaire pour ajouter une salle
    @GetMapping("/lieux")
    public String afficherFormulaireLieux(Model model) {
        model.addAttribute("salle", new Salle());
        return "ajouter-lieu";
    }

    // ✅ Ajouter une salle
    @PostMapping("/lieux")
    public String ajouterSalle(@ModelAttribute("salle") Salle salle) {
        salleService.save(salle);
        return "redirect:/administrateur/lieux?ajout=success";
    }
}
