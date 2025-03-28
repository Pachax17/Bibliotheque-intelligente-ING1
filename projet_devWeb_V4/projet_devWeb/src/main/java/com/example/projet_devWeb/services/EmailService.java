package com.example.projet_devWeb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void envoyerEmailConfirmation(String to, String prenom) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Confirmation d'inscription");

        String body = "Bonjour " + prenom + ",\n\nVotre inscription a bien été enregistrée sur notre site.\n\nMerci ! 😊";
        message.setText(body);

        // 💡 C’est ici qu’on ajoute un nom d’expéditeur personnalisé
        message.setFrom("Ma Bibliothèque - DevWeb <projetdevwebing@gmail.com>");

        emailSender.send(message);
    }
}
