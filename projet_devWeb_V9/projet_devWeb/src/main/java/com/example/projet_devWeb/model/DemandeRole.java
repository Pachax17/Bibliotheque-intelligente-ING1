package com.example.projet_devWeb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DemandeRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Utilisateur utilisateur;

    private boolean traitee = false;

    private LocalDateTime dateDemande = LocalDateTime.now();

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    public boolean isTraitee() { return traitee; }
    public void setTraitee(boolean traitee) { this.traitee = traitee; }

    public LocalDateTime getDateDemande() { return dateDemande; }
    public void setDateDemande(LocalDateTime dateDemande) { this.dateDemande = dateDemande; }
}
