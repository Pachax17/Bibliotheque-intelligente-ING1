package com.bibliotheque.model;

public class Adresse {
    private String nom;
    private String rue;
    private String codePostalVille;

    public Adresse(String nom, String rue, String codePostalVille) {
        this.nom = nom;
        this.rue = rue;
        this.codePostalVille = codePostalVille;
    }

    public String getNom() {
        return nom;
    }

    public String getRue() {
        return rue;
    }

    public String getCodePostalVille() {
        return codePostalVille;
    }
}
