package com.example.projet_devWeb.repository;

import com.example.projet_devWeb.model.ActiviteTracker;
import com.example.projet_devWeb.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository pour l'entité ActiviteTracker.
 * Permet de récupérer un tracker d'activité par utilisateur.
 */
public interface ActiviteTrackerRepository extends JpaRepository<ActiviteTracker, Long> {

    /**
     * Recherche un tracker d'activité pour un utilisateur spécifique.
     *
     * @param utilisateur L'utilisateur concerné.
     * @return Un Optional contenant le tracker, s'il existe.
     */
    Optional<ActiviteTracker> findByUtilisateur(Utilisateur utilisateur);
}
