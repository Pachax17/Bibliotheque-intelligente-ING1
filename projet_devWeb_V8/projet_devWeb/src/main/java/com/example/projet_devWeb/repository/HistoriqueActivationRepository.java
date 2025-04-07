package com.example.projet_devWeb.repository;

import com.example.projet_devWeb.model.HistoriqueActivation;
import com.example.projet_devWeb.model.Objet;
import com.example.projet_devWeb.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriqueActivationRepository extends JpaRepository<HistoriqueActivation, Long> {
    List<HistoriqueActivation> findByUtilisateur(Utilisateur utilisateur);

    // 🔍 Trouver l’historique en cours pour un utilisateur et un objet
    HistoriqueActivation findFirstByUtilisateurAndObjetAndDateFinIsNullOrderByDateDebutDesc(Utilisateur utilisateur, Objet objet);
}
