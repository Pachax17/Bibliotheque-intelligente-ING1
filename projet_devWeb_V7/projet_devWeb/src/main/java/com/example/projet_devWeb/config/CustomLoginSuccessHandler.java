package com.example.projet_devWeb.config;

import com.example.projet_devWeb.services.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        // 🔍 Récupère l'email de l'utilisateur connecté
        String email = authentication.getName();

        // ✅ Appelle le service pour mettre à jour la dernière connexion et les points
        utilisateurService.mettreAJourConnexionEtPoints(email);

        System.out.println("✅ Connexion réussie pour : " + email);

        // 🔁 Redirection après connexion
        response.sendRedirect("/accueil");
    }
}
