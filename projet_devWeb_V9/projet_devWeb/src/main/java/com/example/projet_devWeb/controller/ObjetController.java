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
import java.util.stream.Collectors;

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

    @GetMapping("/proposer")
    public String afficherFormulaireProposition(Model model, Principal principal) {
        model.addAttribute("demandeAjoutObjet", new DemandeAjoutObjet());
        model.addAttribute("salles", salleRepository.findAll());
        return "proposer-objet";
    }

    @PostMapping("/proposer")
    public String soumettreDemandeObjet(@ModelAttribute DemandeAjoutObjet demande,
                                        @RequestParam("salle") Long salleId,
                                        Principal principal) {
        String email = principal.getName();
        Utilisateur utilisateur = utilisateurService.trouverParEmail(email);
        demande.setUtilisateur(utilisateur);

        salleRepository.findById(salleId).ifPresent(demande::setSalle);

        demandeAjoutObjetService.soumettreDemande(demande);
        return "redirect:/accueil?demandeEnvoyee";
    }

    @GetMapping
    public String afficherObjets(@RequestParam(required = false) List<String> type,
                                 @RequestParam(required = false) String salle,
                                 Model model) {
        if (type == null) type = List.of();

        List<Objet> objets = objetService.getObjetsFiltres(type, salle);
        objets.forEach(objet -> {
            if (objet.getUtilisateur() != null) {
                objet.getUtilisateur().getEmail(); // force le chargement
            }
            if (objet.getSalle() != null) {
                objet.getSalle().getNom(); // force le chargement
            }
        });

        List<String> types = List.of("Lumiere", "Temperature", "Visuel");
        List<String> nomsSalles = salleRepository.findAll().stream().map(Salle::getNom).collect(Collectors.toList());

        model.addAttribute("objets", objets);
        model.addAttribute("types", types);
        model.addAttribute("salles", nomsSalles);
        model.addAttribute("type", type);
        model.addAttribute("salle", salle);

        return "Objets";
    }

    @GetMapping("/{id}")
    public String afficherDetailsObjet(@PathVariable Long id, Model model, Principal principal) {
        Optional<Objet> optionalObjet = objetService.findById(id);
        if (optionalObjet.isEmpty()) return "redirect:/Objets?notfound";

        Objet objet = optionalObjet.get();
        model.addAttribute("objet", objet);

        if (principal != null) {
            String email = principal.getName();
            Utilisateur utilisateur = utilisateurService.trouverParEmail(email);
            model.addAttribute("utilisateurConnecte", utilisateur);
        }

        return "objet-detail";
    }

    @PostMapping("/{id}/etat")
    public String changerEtatObjet(@PathVariable Long id,
                                   @RequestParam("actif") boolean actif,
                                   Principal principal) {
        Utilisateur utilisateur = utilisateurService.trouverParEmail(principal.getName());
        objetService.changerEtatObjet(id, actif, utilisateur);
        return "redirect:/Objets/" + id;
    }

    @PostMapping("/{id}/temperature")
    public String ajusterTemperature(@PathVariable Long id,
                                     @RequestParam("valeur") double nouvelleValeur) {
        objetService.findById(id).ifPresent(objet -> {
            if ("Temperature".equalsIgnoreCase(objet.getType()) && objet.isActif()) {
                if (nouvelleValeur >= 16 && nouvelleValeur <= 26) {
                    objet.setTemperature(Math.round(nouvelleValeur * 10.0) / 10.0);
                    objetService.mettreAJourObjet(id, objet);
                }
            }
        });
        return "redirect:/Objets/" + id + "?tempUpdated";
    }

    @PostMapping("/{id}/luminosite")
    public String ajusterLuminosite(@PathVariable Long id,
                                    @RequestParam("valeur") int nouvelleIntensite) {
        objetService.findById(id).ifPresent(objet -> {
            if ("Lumiere".equalsIgnoreCase(objet.getType()) && objet.isActif()) {
                if (nouvelleIntensite >= 0 && nouvelleIntensite <= 100) {
                    objet.setIntensiteLuminosite(nouvelleIntensite);
                    objetService.mettreAJourObjet(id, objet);
                }
            }
        });
        return "redirect:/Objets/" + id + "?lumUpdated";
    }

    @PostMapping("/{id}/visuel")
    public String ajusterVisuelLuminosite(@PathVariable Long id,
                                          @RequestParam("valeur") int nouvelleValeur) {
        objetService.findById(id).ifPresent(objet -> {
            if ("Visuel".equalsIgnoreCase(objet.getType()) && objet.isActif()) {
                if (nouvelleValeur >= 0 && nouvelleValeur <= 100) {
                    objet.setLuminosite(nouvelleValeur);
                    objetService.mettreAJourObjet(id, objet);
                }
            }
        });
        return "redirect:/Objets/" + id + "?visuelUpdated";
    }

    @GetMapping("/{id}/modifier")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        return objetService.findById(id).map(objet -> {
            model.addAttribute("objet", objet);
            model.addAttribute("salles", salleService.findAll());
            return "objet-modifier";
        }).orElse("redirect:/Objets?notfound");
    }

    @PostMapping("/{id}/modifier")
    public String modifierObjet(@PathVariable Long id,
                                @ModelAttribute("objet") Objet objetModifie) {
        objetService.mettreAJourObjet(id, objetModifie);
        return "redirect:/Objets/" + id + "?modifie";
    }
}
