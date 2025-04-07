package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.HistoriqueActivation;
import com.example.projet_devWeb.model.Objet;
import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.repository.HistoriqueActivationRepository;
import com.example.projet_devWeb.repository.ObjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ObjetServiceImpl implements ObjetService {

    @Autowired
    private ObjetRepository objetRepository;

    @Autowired
    private HistoriqueActivationRepository historiqueRepo;

    @Override
    public List<Objet> getObjetsFiltres(List<String> types, String salleNom) {
        if ((types == null || types.isEmpty()) && (salleNom == null || salleNom.isEmpty())) {
            return objetRepository.findAll();
        } else if (types != null && !types.isEmpty() && salleNom != null && !salleNom.isEmpty()) {
            return objetRepository.findByTypeInAndSalle_NomContainingIgnoreCase(types, salleNom);
        } else if (types != null && !types.isEmpty()) {
            return objetRepository.findByTypeIn(types);
        } else {
            return objetRepository.findBySalle_NomContainingIgnoreCase(salleNom);
        }
    }

    @Override
    public List<String> getAllTypesDistincts() {
        List<String> types = objetRepository.findDistinctType();
        return types != null ? types : List.of();
    }

    @Override
    public List<Objet> findAll() {
        return objetRepository.findAll();
    }

    @Override
    public Optional<Objet> findById(Long id) {
        return objetRepository.findById(id);
    }

    @Override
    public void changerEtatObjet(Long id, boolean actif, Utilisateur utilisateur) {
        Optional<Objet> optionalObjet = objetRepository.findById(id);
        if (optionalObjet.isPresent()) {
            Objet objet = optionalObjet.get();

            if (actif) {
                objet.setActif(true);
                objet.setUtilisateurActivant(utilisateur);

                // ✅ Créer historique de début
                HistoriqueActivation historique = new HistoriqueActivation(utilisateur, objet);
                historiqueRepo.save(historique);

            } else {
                Long idActivant = objet.getUtilisateurActivant() != null ? objet.getUtilisateurActivant().getId() : null;
                Long idUtilisateur = utilisateur.getId();
                if (idActivant != null && idActivant.equals(idUtilisateur)) {
                    objet.setActif(false);
                    objet.setUtilisateurActivant(null);

                    // ✅ Mise à jour de la date de fin dans l'historique
                    HistoriqueActivation historique = historiqueRepo.findFirstByUtilisateurAndObjetAndDateFinIsNullOrderByDateDebutDesc(utilisateur, objet);
                    if (historique != null) {
                        historique.setDateFin(LocalDateTime.now());
                        historiqueRepo.save(historique);
                    }
                }
            }

            objetRepository.save(objet);
        }
    }

    @Override
    public void mettreAJourObjet(Long id, Objet objetModifie) {
        Optional<Objet> optionalObjet = objetRepository.findById(id);
        if (optionalObjet.isPresent()) {
            Objet objet = optionalObjet.get();
            objet.setNom(objetModifie.getNom());
            objet.setDescription(objetModifie.getDescription());
            objet.setType(objetModifie.getType());
            objet.setSalle(objetModifie.getSalle());
            objet.setActif(objetModifie.isActif());
            objetRepository.save(objet);
        }
    }

    @Override
    public void supprimerObjet(Long id) {
        objetRepository.deleteById(id);
    }
}