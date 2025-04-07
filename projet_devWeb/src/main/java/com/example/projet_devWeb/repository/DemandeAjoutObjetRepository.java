package com.example.projet_devWeb.repository;

import com.example.projet_devWeb.model.DemandeAjoutObjet;
import com.example.projet_devWeb.model.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DemandeAjoutObjetRepository extends JpaRepository<DemandeAjoutObjet, Long> {
    List<DemandeAjoutObjet> findByStatus(StatutDemande status);
    
}
