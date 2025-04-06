package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.DemandeAjoutObjet;
import java.util.List;

public interface DemandeAjoutObjetService {
    
    List<DemandeAjoutObjet> getDemandesEnAttente();
    
    void accepterDemande(Long id);

    void refuserDemande(Long id);

    // ✅ Ajoute cette ligne pour corriger ton erreur :
    void soumettreDemande(DemandeAjoutObjet demande);
}
