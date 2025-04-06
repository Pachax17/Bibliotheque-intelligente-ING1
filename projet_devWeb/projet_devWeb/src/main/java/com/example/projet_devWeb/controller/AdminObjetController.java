package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.DemandeAjoutObjet;
import com.example.projet_devWeb.services.DemandeAjoutObjetService;
import com.example.projet_devWeb.services.ObjetService;

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


    @GetMapping("/demandes-objets")
    public String afficherDemandes(Model model) {
        List<DemandeAjoutObjet> demandes = demandeService.getDemandesEnAttente();
        model.addAttribute("demandes", demandes);
        return "demandes-objets";
    }


    @PostMapping("/demandes-objets/{id}/accepter")
    public String accepterDemande(@PathVariable Long id) {
        demandeService.accepterDemande(id);
        return "redirect:/administrateur/demandes-objets";
    }

    @PostMapping("/demandes-objets/{id}/refuser")
    public String refuserDemande(@PathVariable Long id) {
        demandeService.refuserDemande(id);
        return "redirect:/administrateur/demandes-objets";
    }


    // ✅ Accès à la gestion des objets existants
    @GetMapping("/GestionObjet")
    public String afficherPageGestionObjets(Model model) {
        model.addAttribute("objets", objetService.findAll());
        return "GestionObjet"; // Ton fichier HTML
    }

}