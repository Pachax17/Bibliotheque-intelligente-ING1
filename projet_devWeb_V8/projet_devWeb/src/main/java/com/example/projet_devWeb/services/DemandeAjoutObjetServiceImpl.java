package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.StatutDemande;
import com.example.projet_devWeb.model.DemandeAjoutObjet;
import com.example.projet_devWeb.model.Objet;
import com.example.projet_devWeb.model.Utilisateur;
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

    @Autowired
    private EmailService emailService;

    @Override
    public List<DemandeAjoutObjet> getDemandesEnAttente() {
        return demandeRepo.findByStatus(StatutDemande.EN_ATTENTE);
    }

    @Override
    public void accepterDemande(Long id) {
        demandeRepo.findById(id).ifPresent(demande -> {
            // Vérifier les objets du même nom dans la même salle
            List<Objet> objetsSimilaires = objetRepo.findByNomAndSalle_Nom(
                demande.getNomObjet(),
                demande.getSalle().getNom()
            );

            String nomFinal = demande.getNomObjet();
            if (!objetsSimilaires.isEmpty()) {
                nomFinal += " " + (objetsSimilaires.size() + 1);
            }

            // Création du nouvel objet
            Objet nouvelObjet = new Objet(
                nomFinal,
                demande.getDescription(),
                demande.getType(),
                demande.getSalle(),
                demande.getUtilisateur()
            );

            objetRepo.save(nouvelObjet);
            demandeRepo.delete(demande);

            // Envoi email confirmation
            Utilisateur user = demande.getUtilisateur();
            String sujet = "✅ Votre demande a été acceptée";
            String message = "Bonjour " + user.getPrenom() + ",\n\n"
                    + "Votre demande pour ajouter l'objet \"" + nomFinal + "\" a été acceptée avec succès ! 🎉\n"
                    + "L’objet a été ajouté et est maintenant disponible.\n\n"
                    + "Merci pour votre contribution à la bibliothèque.\n\n"
                    + "— L’équipe Admin";

            emailService.envoyerEmail(user.getEmail(), sujet, message);
        });
    }

    @Override
    public void refuserDemande(Long id) {
        demandeRepo.findById(id).ifPresent(demande -> {
            Utilisateur user = demande.getUtilisateur();
            String sujet = "❌ Votre demande a été refusée";
            String message = "Bonjour " + user.getPrenom() + ",\n\n"
                    + "Nous vous informons que votre demande pour ajouter l'objet \"" + demande.getNomObjet() + "\" a été refusée.\n"
                    + "Merci néanmoins pour votre proposition, n’hésitez pas à en soumettre d'autres !\n\n"
                    + "— L’équipe Admin";

            demandeRepo.delete(demande);
            emailService.envoyerEmail(user.getEmail(), sujet, message);
        });
    }

    @Override
    public void soumettreDemande(DemandeAjoutObjet demande) {
        demande.setStatus(StatutDemande.EN_ATTENTE);
        demandeRepo.save(demande);
    }
}
