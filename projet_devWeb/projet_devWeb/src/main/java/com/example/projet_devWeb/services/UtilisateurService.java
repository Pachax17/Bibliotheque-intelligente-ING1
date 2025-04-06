package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public void enregistrer(Utilisateur u) {
        utilisateurRepository.save(u);
    }

    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> findByToken(String token) {
        return utilisateurRepository.findByToken(token);
    }

    public Optional<Utilisateur> findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public void validerUtilisateur(Utilisateur utilisateur) {
        utilisateur.setVerifie(true);
        utilisateur.setToken(null); // On peut supprimer le token après validation
        utilisateurRepository.save(utilisateur);
    }

    public void mettreAJourConnexionEtPoints(String email) {
        utilisateurRepository.findByEmail(email).ifPresent(utilisateur -> {
            utilisateur.setDerniereConnexion(LocalDateTime.now());
            utilisateur.setPoints(utilisateur.getPoints() + 1); // Incrémentation des points
            utilisateurRepository.save(utilisateur);
        });
    }

    public Utilisateur trouverParEmail(String email) {
        return utilisateurRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé pour l'email : " + email));
    }
    
}
