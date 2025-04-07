package com.example.projet_devWeb.repository;

import com.example.projet_devWeb.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalleRepository extends JpaRepository<Salle, Long> {

    // 👇 Pour récupérer uniquement les noms des salles existantes
    @Query("SELECT DISTINCT s.nom FROM Salle s")
    List<String> findAllNomSalles();

    // ✅ Pour vérifier si une salle avec un nom précis existe
    boolean existsByNom(String nom);
}
