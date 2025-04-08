package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.ActiviteTracker;
import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.repository.ActiviteTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActiviteTrackerService {

    private static final int MAX_ACTIONS_PAR_JOUR = 10;

    @Autowired
    private ActiviteTrackerRepository trackerRepo;

    @Autowired
    private UtilisateurService utilisateurService;

    public void enregistrerConnexion(Utilisateur utilisateur) {
        ActiviteTracker tracker = trackerRepo.findByUtilisateur(utilisateur).orElseGet(() -> {
            ActiviteTracker t = new ActiviteTracker(utilisateur);
            return trackerRepo.save(t);
        });

        checkReset(tracker);

        if (tracker.getConnexionsAujourdHui() < MAX_ACTIONS_PAR_JOUR) {
            utilisateur.setPoints(utilisateur.getPoints() + 1);
            tracker.setConnexionsAujourdHui(tracker.getConnexionsAujourdHui() + 1);
            utilisateurService.sauvegarder(utilisateur);
        }

        trackerRepo.save(tracker);
    }

    public void enregistrerActivation(Utilisateur utilisateur) {
        ActiviteTracker tracker = trackerRepo.findByUtilisateur(utilisateur).orElseGet(() -> {
            ActiviteTracker t = new ActiviteTracker(utilisateur);
            return trackerRepo.save(t);
        });

        checkReset(tracker);

        if (tracker.getActivationsAujourdHui() < MAX_ACTIONS_PAR_JOUR) {
            utilisateur.setPoints(utilisateur.getPoints() + 1);
            tracker.setActivationsAujourdHui(tracker.getActivationsAujourdHui() + 1);
            utilisateurService.sauvegarder(utilisateur);
        }

        trackerRepo.save(tracker);
    }

    private void checkReset(ActiviteTracker tracker) {
        if (tracker.getDateReset().plusHours(24).isBefore(LocalDateTime.now())) {
            tracker.setConnexionsAujourdHui(0);
            tracker.setActivationsAujourdHui(0);
            tracker.setDateReset(LocalDateTime.now());
        }
    }

    public ActiviteTracker findByUtilisateur(Utilisateur utilisateur) {
        return trackerRepo.findByUtilisateur(utilisateur).orElse(null);
    }
}
