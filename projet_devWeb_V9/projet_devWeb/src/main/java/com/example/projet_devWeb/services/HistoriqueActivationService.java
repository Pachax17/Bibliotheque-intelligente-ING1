package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.HistoriqueActivation;
import com.example.projet_devWeb.model.Utilisateur;

import java.util.List;

public interface HistoriqueActivationService {
    List<HistoriqueActivation> findByUtilisateur(Utilisateur utilisateur);
}
