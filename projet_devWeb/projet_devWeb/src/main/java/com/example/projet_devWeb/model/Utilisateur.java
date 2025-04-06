package com.example.projet_devWeb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private String email;
    private String sexe;

    @Column(name = "motDePasse")
    private String motDePasse;

    @Column(name = "dateInscription")
    private LocalDateTime dateInscription = LocalDateTime.now();

    @Column(name = "dateNaissance")
    private String dateNaissance;

    @Column(name = "token")
    private String token;

    @Column(name = "verifie")
    private boolean verifie = false;

    @Column(name = "derniereConnexion")
    private LocalDateTime derniereConnexion;

    @Column(name = "points")
    private int points = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role = Role.SIMPLE;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau")
    private Niveau niveau = Niveau.DEBUTANT;


    // Enum pour les rôles
    public enum Role {
        SIMPLE,
        AVANCE,
        ADMINISTRATEUR
    }

    //enum niveau
    public enum Niveau {
        DEBUTANT,
        INTERMEDIAIRE,
        AVANCE,
        EXPERT
    }

    // ---------------- Getters & Setters ----------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isVerifie() {
        return verifie;
    }

    public void setVerifie(boolean verifie) {
        this.verifie = verifie;
    }

    public LocalDateTime getDerniereConnexion() {
        return derniereConnexion;
    }

    public void setDerniereConnexion(LocalDateTime derniereConnexion) {
        this.derniereConnexion = derniereConnexion;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }
}
