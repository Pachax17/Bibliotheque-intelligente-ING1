package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.Salle;
import com.example.projet_devWeb.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalleServiceImpl implements SalleService {

    @Autowired
    private SalleRepository salleRepository;

    @Override
    public List<Salle> findAll() {
        return salleRepository.findAll();
    }

    @Override
    public List<String> getAllNomsDeSalles() {
        return salleRepository.findAllNomSalles();
    }

    @Override
    public void save(Salle salle) {
        String nomBase = salle.getNom();
        String nomFinal = nomBase;
        int compteur = 1;

        // Si le nom existe déjà, on l'incrémente : Salle Informatique, Salle Informatique 2, etc.
        while (salleRepository.existsByNom(nomFinal)) {
            compteur++;
            nomFinal = nomBase + " " + compteur;
        }

        salle.setNom(nomFinal);
        salleRepository.save(salle);
    }
}
