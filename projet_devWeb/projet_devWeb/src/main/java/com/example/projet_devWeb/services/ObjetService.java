package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.Objet;

import java.util.List;
import java.util.Optional;

public interface ObjetService {

    // 🔍 Récupère les objets avec filtres type + salle
    List<Objet> getObjetsFiltres(List<String> types, String salleNom);

    // 📦 Liste des types distincts pour les filtres
    List<String> getAllTypesDistincts();

    // 📚 Tous les objets
    List<Objet> findAll();

    // 🔎 Récupérer un objet par son ID (pour page détail)
    Optional<Objet> findById(Long id);

    // ✅ Modifier l’état actif/inactif
    void changerEtatObjet(Long id, boolean actif);

    // ✏️ Met à jour un objet existant (nom, description, type, salle...)
    void mettreAJourObjet(Long id, Objet objetModifie);
}
