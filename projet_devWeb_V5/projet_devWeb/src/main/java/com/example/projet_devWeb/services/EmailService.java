package com.example.projet_devWeb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envoie un email de confirmation avec lien de vérification
     * @param destinataire l'email du destinataire
     * @param prenom le prénom à afficher
     * @param token le token de vérification
     */
    public void envoyerEmailConfirmation(String destinataire, String prenom, String token) {
        new Thread(() -> {
            try {
                String sujet = "Confirmation d'inscription";
                String lien = "http://localhost:8080/verification?token=" + token;

                String corps = "Bonjour " + prenom + ",\n\n" +
                        "Merci pour votre inscription !\n\n" +
                        "Veuillez cliquer sur le lien ci-dessous pour valider votre compte :\n" +
                        lien + "\n\n" +
                        "À très vite !";

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(destinataire);
                message.setSubject(sujet);
                message.setText(corps);
                message.setFrom("Ma Bibliothèque - DevWeb <projetdevwebing@gmail.com>");

                mailSender.send(message);
            } catch (Exception e) {
                System.err.println("❌ Erreur lors de l'envoi de l'email : " + e.getMessage());
            }
        }).start();
    }
}
