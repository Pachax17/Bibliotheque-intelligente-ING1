package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    void enregistrer(Utilisateur utilisateur);

    List<Utilisateur> getAll();

    List<Utilisateur> findAll();

    Optional<Utilisateur> findByToken(String token);

    Optional<Utilisateur> findByEmail(String email);

    Optional<Utilisateur> findById(Long id);

    Utilisateur trouverParEmail(String email);

    void validerUtilisateur(Utilisateur utilisateur);

    void mettreAJourConnexionEtPoints(String email);

    void supprimerUtilisateurEtDesactiverObjets(int id);

    void mettreAJourUtilisateur(Long id, Utilisateur modif);

    void sauvegarder(Utilisateur utilisateur); // ✅ Ajouté
}
