package com.projetspring.demo.service;


import org.springframework.stereotype.Service;

@Service
public class StringManipulationService {

    // Méthode pour transformer un nom en majuscules et renvoyer la longueur du nom
    public String processName(String prenom) {
        if (prenom == null || prenom.isEmpty()) {
            return "Le nom ne peut pas être vide.";
        }

        // Calcul de la longueur du nom
        int nameLength = prenom.length();

        // Retourne un message formaté
        return String.format("Nom transformé : %s. Longueur du nom : %d caractères.", prenom, nameLength);
    }
}

