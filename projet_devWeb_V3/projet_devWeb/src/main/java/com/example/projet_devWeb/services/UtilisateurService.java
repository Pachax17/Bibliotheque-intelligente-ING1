package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


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
}

