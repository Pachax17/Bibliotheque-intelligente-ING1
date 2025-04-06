package com.example.projet_devWeb.repository;

import com.example.projet_devWeb.model.Objet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjetRepository extends JpaRepository<Objet, Long> {

    List<Objet> findByType(String type);
    List<Objet> findBySalle_Nom(String nomSalle);
    List<Objet> findByTypeAndSalle_Nom(String type, String nomSalle);
    List<Objet> findByTypeContainingIgnoreCase(String type);
    List<Objet> findBySalle_NomContainingIgnoreCase(String nomSalle);
    List<Objet> findByTypeContainingIgnoreCaseAndSalle_NomContainingIgnoreCase(String type, String nomSalle);

    // ✅ Pour filtrer par plusieurs types
    List<Objet> findByTypeIn(List<String> types);

    // ✅ Pour filtrer par plusieurs types + salle
    List<Objet> findByTypeInAndSalle_NomContainingIgnoreCase(List<String> types, String salleNom);

    // ✅ Pour récupérer tous les types distincts
    @Query("SELECT DISTINCT o.type FROM Objet o WHERE o.type IS NOT NULL")
    List<String> findDistinctType();

}




