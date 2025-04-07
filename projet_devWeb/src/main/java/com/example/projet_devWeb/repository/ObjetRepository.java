package com.example.projet_devWeb.repository;

import com.example.projet_devWeb.model.Objet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ObjetRepository extends JpaRepository<Objet, Long> {

    List<Objet> findByTypeInAndSalle_NomContainingIgnoreCase(List<String> types, String salleNom);
    List<Objet> findByTypeIn(List<String> types);
    List<Objet> findBySalle_NomContainingIgnoreCase(String salleNom);

    @Query("SELECT DISTINCT o.type FROM Objet o")
    List<String> findDistinctType();

    // ✅ Pour l'incrémentation du nom d'objet dans une salle spécifique
    List<Objet> findByNomAndSalle_Nom(String nom, String salleNom);

    // ✅ Pour désactiver tous les objets activés par un utilisateur supprimé
    List<Objet> findByUtilisateurActivant_IdAndActifTrue(int utilisateurId);
}
