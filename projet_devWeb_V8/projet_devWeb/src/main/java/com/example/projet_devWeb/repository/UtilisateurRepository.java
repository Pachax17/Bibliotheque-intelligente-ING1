package com.example.projet_devWeb.repository;

import com.example.projet_devWeb.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> { // ← ici on remplace Integer par Long

    Optional<Utilisateur> findByToken(String token);

    Optional<Utilisateur> findByEmail(String email);
}
