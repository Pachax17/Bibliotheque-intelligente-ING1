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
import java.util.Random;

@Service
public class ObjetServiceImpl implements ObjetService {

    @Autowired
    private ObjetRepository objetRepository;

    @Autowired
    private HistoriqueActivationRepository historiqueRepo;

    private final Random random = new Random();

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
                // Si déjà actif par quelqu’un d’autre (y compris anonyme), ne rien faire
                if (objet.isActif() && objet.getUtilisateurActivant() != null &&
                        (utilisateur == null || !objet.getUtilisateurActivant().getId().equals(utilisateur.getId()))) {
                    return;
                }

                objet.setActif(true);
                objet.setUtilisateurActivant(utilisateur);

                // Pas d'initialisation aléatoire ici (valeurs déjà définies à la désactivation)

                if (utilisateur != null) {
                    historiqueRepo.save(new HistoriqueActivation(utilisateur, objet));
                }

            } else {
                Long idActivant = objet.getUtilisateurActivant() != null ? objet.getUtilisateurActivant().getId() : null;
                Long idUtilisateur = utilisateur != null ? utilisateur.getId() : null;

                // Seul celui qui a activé peut désactiver
                if ((idActivant == null && idUtilisateur == null) ||
                    (idActivant != null && idActivant.equals(idUtilisateur))) {

                    objet.setActif(false);
                    objet.setUtilisateurActivant(null);

                    // 🔁 Réinitialisation aléatoire
                    if ("Temperature".equalsIgnoreCase(objet.getType())) {
                        double temp = 15 + (5 * random.nextDouble());
                        objet.setTemperature(Math.round(temp * 10.0) / 10.0);
                    }

                    if ("Lumiere".equalsIgnoreCase(objet.getType())) {
                        int intensite = 10 + random.nextInt(31); // 10–40
                        objet.setIntensiteLuminosite(intensite);
                    }

                    if ("Visuel".equalsIgnoreCase(objet.getType())) {
                        int luminosite = 20 + random.nextInt(31); // 20–50
                        objet.setLuminosite(luminosite);
                    }

                    if (utilisateur != null) {
                        HistoriqueActivation historique = historiqueRepo
                                .findFirstByUtilisateurAndObjetAndDateFinIsNullOrderByDateDebutDesc(utilisateur, objet);
                        if (historique != null) {
                            historique.setDateFin(LocalDateTime.now());
                            historiqueRepo.save(historique);
                        }
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

            if ("Temperature".equalsIgnoreCase(objet.getType())) {
                objet.setTemperature(objetModifie.getTemperature());
            }

            if ("Lumiere".equalsIgnoreCase(objet.getType())) {
                objet.setIntensiteLuminosite(objetModifie.getIntensiteLuminosite());
            }

            if ("Visuel".equalsIgnoreCase(objet.getType())) {
                objet.setLuminosite(objetModifie.getLuminosite());
            }

            objetRepository.save(objet);
        }
    }

    @Override
    public void supprimerObjet(Long id) {
        objetRepository.deleteById(id);
    }
}
