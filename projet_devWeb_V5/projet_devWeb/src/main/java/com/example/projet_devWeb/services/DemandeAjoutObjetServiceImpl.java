package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.StatutDemande;
import com.example.projet_devWeb.model.DemandeAjoutObjet;
import com.example.projet_devWeb.model.Objet;
import com.example.projet_devWeb.repository.DemandeAjoutObjetRepository;
import com.example.projet_devWeb.repository.ObjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeAjoutObjetServiceImpl implements DemandeAjoutObjetService {

    @Autowired
    private DemandeAjoutObjetRepository demandeRepo;

    @Autowired
    private ObjetRepository objetRepo;

    @Override
    public List<DemandeAjoutObjet> getDemandesEnAttente() {
        return demandeRepo.findByStatus(StatutDemande.EN_ATTENTE);
    }

    @Override
    public void accepterDemande(Long id) {
        demandeRepo.findById(id).ifPresent(demande -> {
            // ➕ Création de l'objet à partir de la demande (avec utilisateur)
            Objet nouvelObjet = new Objet(
                demande.getNomObjet(),
                demande.getDescription(),
                demande.getType(),
                demande.getSalle(),
                demande.getUtilisateur() // ✅ on ajoute l'utilisateur ici
            );

            objetRepo.save(nouvelObjet);      // ✅ Sauvegarde l’objet
            demandeRepo.delete(demande);      // 🗑️ Supprime la demande
        });
    }

    @Override
    public void refuserDemande(Long id) {
        demandeRepo.findById(id).ifPresent(demande -> {
            demandeRepo.delete(demande); // ❌ Supprime la demande refusée
        });
    }

    @Override
    public void soumettreDemande(DemandeAjoutObjet demande) {
        demande.setStatus(StatutDemande.EN_ATTENTE);
        demandeRepo.save(demande);
    }
}
