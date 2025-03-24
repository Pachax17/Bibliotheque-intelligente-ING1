package com.projetspring.demo.model;

/**
 * La classe User représente un utilisateur avec ses informations de base.
 * Elle inclut des propriétés telles que le nom, prénom, email, mot de passe et date de naissance.
 * Cette classe sert à définir un utilisateur pour être utilisé dans l'application, 
 * par exemple, pour être stocké dans une base de données ou un fichier.
 */
public class User {
    private String id;        // Identifiant unique de l'utilisateur
    private String nom;       // Nom de l'utilisateur
    private String prenom;    // Prénom de l'utilisateur
    private String email;     // Adresse email de l'utilisateur
    private String password;  // Mot de passe de l'utilisateur
    private String naissance; // Date de naissance de l'utilisateur

    // Constructeur par défaut (sans paramètres)
    // Il permet de créer un objet User sans initialiser ses propriétés
    public User() {}

    // Constructeur avec paramètres
    // Ce constructeur permet de créer un objet User en initialisant toutes ses propriétés
    public User(String id, String nom, String prenom, String email, String password, String naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.naissance = naissance;
    }

    // Getter pour l'attribut id
    public String getId() { return id; }
    // Setter pour l'attribut id
    public void setId(String id) { this.id = id; }


    // Getter pour l'attribut nom
    public String getNom() { return nom; }
    // Setter pour l'attribut nom
    public void setNom(String nom) { this.nom = nom; }


    // Getter pour l'attribut prenom
    public String getPrenom() { return prenom; }
    // Setter pour l'attribut prenom
    public void setPrenom(String prenom) { this.prenom = prenom; }


    // Getter pour l'attribut email
    public String getEmail() { return email; }
    // Setter pour l'attribut email
    public void setEmail(String email) { this.email = email; }


    // Getter pour l'attribut password
    public String getPassword() { return password; }
    // Setter pour l'attribut password
    public void setPassword(String password) { this.password = password; }


    // Getter pour l'attribut naissance
    public String getNaissance() { return naissance; }
    // Setter pour l'attribut naissance
    public void setNaissance(String naissance) { this.naissance = naissance; }
}
