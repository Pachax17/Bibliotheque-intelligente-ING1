package com.example.projet_devWeb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Objet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private String type;
    private boolean actif = false;

    @Column(nullable = true)
    private Double temperature; // 🌡️ Température pour les objets de type "Temperature"

    @Column(nullable = true)
    private Integer intensiteLuminosite; // 💡 Intensité pour les objets de type "Lumiere"

    @Column(nullable = true)
    private Integer luminosite; // 🖼️ Luminosité pour les objets de type "Visuel"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSalle")
    private Salle salle;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "idUtilisateur")
    @NotFound(action = NotFoundAction.IGNORE) // ✅ Ignore si l'utilisateur est supprimé
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "idUtilisateurActivant")
    @NotFound(action = NotFoundAction.IGNORE) // ✅ Idem ici
    private Utilisateur utilisateurActivant;

    public Objet() {}

    public Objet(String nom, String description, String type, Salle salle, Utilisateur utilisateur) {
        this.nom = nom;
        this.description = description;
        this.type = type;
        this.salle = salle;
        this.utilisateur = utilisateur;
        this.actif = false;
        this.utilisateurActivant = null;
        this.temperature = null;
        this.intensiteLuminosite = null;
        this.luminosite = null;
    }

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Integer getIntensiteLuminosite() { return intensiteLuminosite; }
    public void setIntensiteLuminosite(Integer intensiteLuminosite) { this.intensiteLuminosite = intensiteLuminosite; }

    public Integer getLuminosite() { return luminosite; }
    public void setLuminosite(Integer luminosite) { this.luminosite = luminosite; }

    public Salle getSalle() { return salle; }
    public void setSalle(Salle salle) { this.salle = salle; }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    public Utilisateur getUtilisateurActivant() { return utilisateurActivant; }
    public void setUtilisateurActivant(Utilisateur utilisateurActivant) { this.utilisateurActivant = utilisateurActivant; }
}
