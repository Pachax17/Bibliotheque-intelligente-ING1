package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.Objet;
import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.repository.ObjetRepository;
import com.example.projet_devWeb.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ObjetRepository objetRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void enregistrer(Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }

    @Override
    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Optional<Utilisateur> findByToken(String token) {
        return utilisateurRepository.findByToken(token);
    }

    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    @Override
    public Optional<Utilisateur> findById(Long id) {
        return utilisateurRepository.findById(id);
    }

    @Override
    public Utilisateur trouverParEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé pour l'email : " + email));
    }

    @Override
    public void validerUtilisateur(Utilisateur utilisateur) {
        utilisateur.setVerifie(true);
        utilisateur.setToken(null);
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public void mettreAJourConnexionEtPoints(String email) {
        utilisateurRepository.findByEmail(email).ifPresent(utilisateur -> {
            utilisateur.setDerniereConnexion(LocalDateTime.now());
            utilisateur.setPoints(utilisateur.getPoints() + 1);
            utilisateurRepository.save(utilisateur);
        });
    }

    @Override
    public void supprimerUtilisateurEtDesactiverObjets(int id) {
        utilisateurRepository.findById((long) id).ifPresent(utilisateur -> {
            // 🔌 Désactiver les objets activés par cet utilisateur
            List<Objet> objetsActifs = objetRepository.findByUtilisateurActivant_IdAndActifTrue(id);
            for (Objet objet : objetsActifs) {
                objet.setActif(false);
                objet.setUtilisateurActivant(null);
            }
            objetRepository.saveAll(objetsActifs);

            // 📧 Envoyer un mail
            String sujet = "❌ Suppression de votre compte";
            String message = "Bonjour " + utilisateur.getPrenom() + ",\n\n"
                    + "Votre compte a été supprimé pour non-respect des conditions générales d'utilisation.\n"
                    + "Si vous pensez qu’il s’agit d’une erreur, veuillez nous contacter.\n\n"
                    + "Cordialement,\nL’équipe Admin";

            emailService.envoyerEmail(utilisateur.getEmail(), sujet, message);

            // 🗑️ Suppression
            utilisateurRepository.delete(utilisateur);
        });
    }

    @Override
    public void mettreAJourUtilisateur(Long id, Utilisateur modifie) {
        utilisateurRepository.findById(id).ifPresent(utilisateur -> {
            utilisateur.setNom(modifie.getNom());
            utilisateur.setPrenom(modifie.getPrenom());
            utilisateur.setEmail(modifie.getEmail());
            utilisateur.setSexe(modifie.getSexe());
            utilisateur.setDateNaissance(modifie.getDateNaissance());
            utilisateur.setRole(modifie.getRole());
            utilisateurRepository.save(utilisateur);
        });
    }
}
