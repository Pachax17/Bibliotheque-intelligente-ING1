package com.example.projet_devWeb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Objet")
@Inheritance(strategy = InheritanceType.JOINED)
public class Objet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(name = "derniere_interaction")
    private LocalDateTime derniereInteraction;

    @Column(nullable = false)
    private boolean etatFonctionnement;

    @Column(nullable = false)
    private boolean etatUtilisation;

    @ManyToOne
    @JoinColumn(name = "salle_id", nullable = false)
    private Salle salle;

    // Getters et Setters
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

    public LocalDateTime getDerniereInteraction() {
        return derniereInteraction;
    }

    public void setDerniereInteraction(LocalDateTime derniereInteraction) {
        this.derniereInteraction = derniereInteraction;
    }

    public boolean isEtatFonctionnement() {
        return etatFonctionnement;
    }

    public void setEtatFonctionnement(boolean etatFonctionnement) {
        this.etatFonctionnement = etatFonctionnement;
    }

    public boolean isEtatUtilisation() {
        return etatUtilisation;
    }

    public void setEtatUtilisation(boolean etatUtilisation) {
        this.etatUtilisation = etatUtilisation;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }
}
