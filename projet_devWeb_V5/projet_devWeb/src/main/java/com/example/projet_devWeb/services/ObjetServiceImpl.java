package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.Objet;
import com.example.projet_devWeb.repository.ObjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjetServiceImpl implements ObjetService {

    @Autowired
    private ObjetRepository objetRepository;

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
        return types != null ? types : List.of(); // ✅ Retourne une liste vide si null
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
    public void changerEtatObjet(Long id, boolean actif) {
        Optional<Objet> optionalObjet = objetRepository.findById(id);
        if (optionalObjet.isPresent()) {
            Objet objet = optionalObjet.get();
            objet.setActif(actif); // ⚠️ Assure-toi que le champ "actif" existe bien dans Objet.java
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
            objet.setActif(objetModifie.isActif()); // met à jour aussi l'état si nécessaire
            objetRepository.save(objet);
        }
    }
}
