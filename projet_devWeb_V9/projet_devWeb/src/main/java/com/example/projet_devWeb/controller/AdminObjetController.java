package com.example.projet_devWeb.controller;

import com.example.projet_devWeb.model.DemandeAjoutObjet;
import com.example.projet_devWeb.model.Objet;
import com.example.projet_devWeb.model.Salle;
import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.services.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @Autowired
    private UtilisateurService utilisateurService;

    // ✅ Liste des demandes d’ajout d’objets
    @GetMapping("/demandes-objets")
    public String afficherDemandesAjoutObjet(Model model) {
        List<DemandeAjoutObjet> demandes = demandeService.getDemandesEnAttente();
        model.addAttribute("demandes", demandes);
        return "demandes-objets";
    }

    @PostMapping("/demandes-objets/{id}/accepter")
    public String accepterDemandeAjoutObjet(@PathVariable Long id) {
        demandeService.accepterDemande(id);
        return "redirect:/administrateur/demandes-objets";
    }

    @PostMapping("/demandes-objets/{id}/refuser")
    public String refuserDemandeAjoutObjet(@PathVariable Long id) {
        demandeService.refuserDemande(id);
        return "redirect:/administrateur/demandes-objets";
    }

    @GetMapping("/GestionObjet")
    public String afficherPageGestionObjets(Model model) {
        model.addAttribute("objets", objetService.findAll());
        return "GestionObjet";
    }

    @PostMapping("/objet/{id}/supprimer")
    public String supprimerObjet(@PathVariable Long id) {
        objetService.supprimerObjet(id);
        return "redirect:/administrateur/GestionObjet?supprime";
    }

    @GetMapping("/objet/{id}/modifier")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        return objetService.findById(id).map(objet -> {
            model.addAttribute("objet", objet);
            model.addAttribute("salles", salleService.findAll());
            return "modifier-objet";
        }).orElse("redirect:/administrateur/GestionObjet?notfound");
    }

    @PostMapping("/objet/{id}/modifier")
    public String modifierObjet(@PathVariable Long id, @ModelAttribute("objet") Objet objetModifie) {
        objetService.mettreAJourObjet(id, objetModifie);
        return "redirect:/administrateur/GestionObjet?modifie";
    }

    @GetMapping("/lieux")
    public String afficherFormulaireLieux(Model model) {
        model.addAttribute("salle", new Salle());
        return "ajouter-lieu";
    }

    @PostMapping("/lieux")
    public String ajouterSalle(@ModelAttribute("salle") Salle salle) {
        salleService.save(salle);
        return "redirect:/administrateur/lieux?ajout=success";
    }

    // ✅ Génération PDF avec 3 tableaux (utilisateurs, objets, salles)
    @GetMapping("/rapport-pdf")
    public void genererRapportPdf(HttpServletResponse response) throws IOException, DocumentException {
        String fileName = "rapport_admin_" + java.time.LocalDate.now() + ".pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    
        List<Utilisateur> utilisateurs = utilisateurService.findAll();
        List<Objet> objets = objetService.findAll();
        List<Salle> salles = salleService.findAll();
    
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
    
        Font titreFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font cellFont = new Font(Font.FontFamily.HELVETICA, 11);
    
        // 📄 Titre principal
        document.add(new Paragraph("📄 Rapport Administrateur", titreFont));
        document.add(new Paragraph(" "));
    
        // 📊 Résumé
        int totalUsers = utilisateurs.size();
        int totalObjets = objets.size();
        int totalSalles = salles.size();
    
        Paragraph resume = new Paragraph("📊 Résumé", headerFont);
        resume.add(new Paragraph("👥 Utilisateurs : " + totalUsers, cellFont));
        resume.add(new Paragraph("📦 Objets : " + totalObjets, cellFont));
        resume.add(new Paragraph("🏛 Salles : " + totalSalles, cellFont));
        resume.setSpacingAfter(15f);
        document.add(resume);
    
        // 👥 UTILISATEURS
        document.add(new Paragraph("👥 Utilisateurs", headerFont));
        PdfPTable userTable = new PdfPTable(11);
        userTable.setWidthPercentage(100);
        userTable.setSpacingBefore(10f);
        userTable.setSpacingAfter(10f);
    
        String[] userHeaders = {"ID", "Nom", "Prénom", "Email", "Sexe", "Date de naissance", "Date d’inscription", "Vérifié", "Dernière connexion", "Points", "Rôle"};
        for (String h : userHeaders) userTable.addCell(new PdfPCell(new Phrase(h, headerFont)));
    
        for (Utilisateur u : utilisateurs) {
            userTable.addCell(new PdfPCell(new Phrase(String.valueOf(u.getId()), cellFont)));
            userTable.addCell(new PdfPCell(new Phrase(u.getNom(), cellFont)));
            userTable.addCell(new PdfPCell(new Phrase(u.getPrenom(), cellFont)));
            userTable.addCell(new PdfPCell(new Phrase(u.getEmail(), cellFont)));
            userTable.addCell(new PdfPCell(new Phrase(u.getSexe(), cellFont)));
            userTable.addCell(new PdfPCell(new Phrase(String.valueOf(u.getDateNaissance()), cellFont)));
            userTable.addCell(new PdfPCell(new Phrase(String.valueOf(u.getDateInscription()), cellFont)));
            userTable.addCell(new PdfPCell(new Phrase(u.isVerifie() ? "Oui" : "Non", cellFont)));
            userTable.addCell(new PdfPCell(new Phrase(String.valueOf(u.getDerniereConnexion()), cellFont)));
            userTable.addCell(new PdfPCell(new Phrase(String.valueOf(u.getPoints()), cellFont)));
            userTable.addCell(new PdfPCell(new Phrase(u.getRole().toString(), cellFont)));
        }
        document.add(userTable);
    
        // 📦 OBJETS
        document.add(new Paragraph("📦 Objets", headerFont));
        PdfPTable objetTable = new PdfPTable(6);
        objetTable.setWidthPercentage(100);
        objetTable.setSpacingBefore(10f);
        objetTable.setSpacingAfter(10f);
    
        String[] objetHeaders = {"ID", "Nom", "Salle", "Ajouté par", "Type", "Actif"};
        for (String h : objetHeaders) objetTable.addCell(new PdfPCell(new Phrase(h, headerFont)));
    
        for (Objet o : objets) {
            objetTable.addCell(new PdfPCell(new Phrase(String.valueOf(o.getId()), cellFont)));
            objetTable.addCell(new PdfPCell(new Phrase(o.getNom(), cellFont)));
    
            String salleNom = (o.getSalle() != null) ? o.getSalle().getNom() : "Non spécifiée";
            objetTable.addCell(new PdfPCell(new Phrase(salleNom, cellFont)));
    
            String auteur = (o.getUtilisateur() != null) ?
                    o.getUtilisateur().getNom() + " " + o.getUtilisateur().getPrenom() : "N/A";
            objetTable.addCell(new PdfPCell(new Phrase(auteur, cellFont)));
    
            objetTable.addCell(new PdfPCell(new Phrase(o.getType(), cellFont)));
            objetTable.addCell(new PdfPCell(new Phrase(o.isActif() ? "Oui" : "Non", cellFont)));
        }
        document.add(objetTable);
    
        // 🏛 SALLES
        document.add(new Paragraph("🏛 Salles", headerFont));
        PdfPTable salleTable = new PdfPTable(2);
        salleTable.setWidthPercentage(100);
        salleTable.setSpacingBefore(10f);
        salleTable.setSpacingAfter(10f);
        salleTable.addCell(new PdfPCell(new Phrase("Nom", headerFont)));
        salleTable.addCell(new PdfPCell(new Phrase("Capacité", headerFont)));
    
        for (Salle s : salles) {
            salleTable.addCell(new PdfPCell(new Phrase(s.getNom(), cellFont)));
            salleTable.addCell(new PdfPCell(new Phrase(String.valueOf(s.getCapacite()), cellFont)));
        }
        document.add(salleTable);
    
        document.close();
    }
    
}
