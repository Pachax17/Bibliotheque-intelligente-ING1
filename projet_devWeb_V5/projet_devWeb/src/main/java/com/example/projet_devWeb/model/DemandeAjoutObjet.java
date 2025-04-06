package com.example.projet_devWeb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DemandeAjoutObjet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomObjet;
    private String description;
    private String type;

    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "idSalle")
    private Salle salle;

    @Enumerated(EnumType.STRING)
    private StatutDemande status = StatutDemande.EN_ATTENTE;

    private LocalDateTime dateDemande = LocalDateTime.now();

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomObjet() { return nomObjet; }
    public void setNomObjet(String nomObjet) { this.nomObjet = nomObjet; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    public Salle getSalle() { return salle; }
    public void setSalle(Salle salle) { this.salle = salle; }

    public StatutDemande getStatus() { return status; }
    public void setStatus(StatutDemande status) { this.status = status; }

    public LocalDateTime getDateDemande() { return dateDemande; }
    public void setDateDemande(LocalDateTime dateDemande) { this.dateDemande = dateDemande; }
}
