package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.DemandeRole;
import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.repository.DemandeRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeRoleService {

    @Autowired
    private DemandeRoleRepository repo;

    // 🔼 Enregistre une nouvelle demande
    public void enregistrerDemande(Utilisateur utilisateur) {
        DemandeRole demande = new DemandeRole();
        demande.setUtilisateur(utilisateur);
        repo.save(demande);
    }

    // 🔍 Récupère toutes les demandes non encore traitées
    public List<DemandeRole> getDemandesNonTraitees() {
        return repo.findByTraiteeFalse();
    }

    // ✅ Accepter une demande : rôle AVANCE + marquer comme traitée
    public void validerDemande(Long demandeId) {
        DemandeRole demande = repo.findById(demandeId).orElseThrow();
        Utilisateur u = demande.getUtilisateur();
        u.setRole(Utilisateur.Role.AVANCE);
        demande.setTraitee(true);
        repo.save(demande);
    }

    // 🔍 Utilisé pour lire une demande spécifique
    public DemandeRole getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    // ❌ Refuser une demande : juste la marquer comme traitée
    public void refuserDemande(Long demandeId) {
        DemandeRole demande = repo.findById(demandeId).orElseThrow();
        demande.setTraitee(true);
        repo.save(demande);
    }
}
