package com.example.projet_devWeb.model;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "HistoriqueActivation") // 👈 avec MAJUSCULE
public class HistoriqueActivation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Objet objet;
    

    @Column(name = "dateDebut")
    private LocalDateTime dateDebut;

    @Column(name = "dateFin")
    private LocalDateTime dateFin;


    public HistoriqueActivation() {}

    public HistoriqueActivation(Utilisateur utilisateur, Objet objet) {
        this.utilisateur = utilisateur;
        this.objet = objet;
        this.dateDebut = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Objet getObjet() {
        return objet;
    }

    public void setObjet(Objet objet) {
        this.objet = objet;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public Duration getDuree() {
        if (dateDebut != null && dateFin != null) {
            return Duration.between(dateDebut, dateFin);
        }
        return null;
    }
}
