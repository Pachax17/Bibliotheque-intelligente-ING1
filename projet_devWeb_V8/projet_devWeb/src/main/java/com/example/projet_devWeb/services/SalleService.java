package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.Salle;

import java.util.List;

public interface SalleService {
    List<Salle> findAll();

    List<String> getAllNomsDeSalles();

    void save(Salle salle); // ✅ Ajout de la méthode manquante
}
