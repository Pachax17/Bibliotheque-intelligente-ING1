package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.HistoriqueActivation;
import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.repository.HistoriqueActivationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriqueActivationServiceImpl implements HistoriqueActivationService {

    @Autowired
    private HistoriqueActivationRepository historiqueRepo;

    @Override
    public List<HistoriqueActivation> findByUtilisateur(Utilisateur utilisateur) {
        return historiqueRepo.findByUtilisateur(utilisateur);
    }
}
