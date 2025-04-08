package com.example.projet_devWeb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ActiviteTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Utilisateur utilisateur;

    private int connexionsAujourdHui;
    private int activationsAujourdHui;

    private LocalDateTime dateReset;

    public ActiviteTracker() {}

    public ActiviteTracker(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.dateReset = LocalDateTime.now();
    }

    // Getters et setters
    public Long getId() { return id; }

    public Utilisateur getUtilisateur() { return utilisateur; }

    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    public int getConnexionsAujourdHui() { return connexionsAujourdHui; }

    public void setConnexionsAujourdHui(int connexionsAujourdHui) { this.connexionsAujourdHui = connexionsAujourdHui; }

    public int getActivationsAujourdHui() { return activationsAujourdHui; }

    public void setActivationsAujourdHui(int activationsAujourdHui) { this.activationsAujourdHui = activationsAujourdHui; }

    public LocalDateTime getDateReset() { return dateReset; }

    public void setDateReset(LocalDateTime dateReset) { this.dateReset = dateReset; }
}
