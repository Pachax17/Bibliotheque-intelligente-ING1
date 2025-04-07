package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.Objet;
import com.example.projet_devWeb.model.Utilisateur;
import java.util.List;
import java.util.Optional;

public interface ObjetService {
    List<Objet> getObjetsFiltres(List<String> types, String salleNom);
    List<String> getAllTypesDistincts();
    List<Objet> findAll();
    Optional<Objet> findById(Long id);
    void changerEtatObjet(Long id, boolean actif, Utilisateur utilisateur);
    void mettreAJourObjet(Long id, Objet objetModifie);
    
    // Ajoute cette méthode pour la suppression
    void supprimerObjet(Long id);
}
