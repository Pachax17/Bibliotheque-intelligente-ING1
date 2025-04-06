package com.example.projet_devWeb.model;

import jakarta.persistence.*;

@Entity
public class Objet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String description; // ✅ Pour afficher sur les pages détails et modifications

    private String type;

    private boolean actif = false; // ✅ Pour savoir si l’objet est activé ou non

    @ManyToOne
    @JoinColumn(name = "idSalle")
    private Salle salle;

    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur; // ✅ Qui a proposé l’objet

    public Objet() {
    }

    // 🔧 Constructeur complet
    public Objet(String nom, String description, String type, Salle salle, Utilisateur utilisateur) {
        this.nom = nom;
        this.description = description;
        this.type = type;
        this.salle = salle;
        this.utilisateur = utilisateur;
        this.actif = false; // Par défaut désactivé
    }

    // ✅ Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
