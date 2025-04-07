package com.example.projet_devWeb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.projet_devWeb.model.Utilisateur;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envoie un email de confirmation avec lien de vérification
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

    /**
     * Envoie un email contenant le lien de réinitialisation du mot de passe
     */
    public void envoyerLienDeReset(Utilisateur utilisateur) {
        new Thread(() -> {
            try {
                String sujet = "Réinitialisation de votre mot de passe";
                String lien = "http://localhost:8080/reinitialisation?token=" + utilisateur.getToken();

                String corps = "Bonjour " + utilisateur.getPrenom() + ",\n\n" +
                        "Vous avez demandé à réinitialiser votre mot de passe.\n\n" +
                        "Cliquez sur le lien ci-dessous pour créer un nouveau mot de passe :\n" +
                        lien + "\n\n" +
                        "Si vous n'êtes pas à l'origine de cette demande, ignorez simplement ce message.";

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(utilisateur.getEmail());
                message.setSubject(sujet);
                message.setText(corps);
                message.setFrom("Ma Bibliothèque - DevWeb <projetdevwebing@gmail.com>");

                mailSender.send(message);
            } catch (Exception e) {
                System.err.println("❌ Erreur lors de l'envoi de l'email de réinitialisation : " + e.getMessage());
            }
        }).start();
    }

    /**
     * Envoie un e-mail simple avec sujet et contenu personnalisés
     */
    public void envoyerEmail(String destinataire, String sujet, String corps) {
        new Thread(() -> {
            try {
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
