package com.example.projet_devWeb.listener;

import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.services.ActiviteTrackerService;
import com.example.projet_devWeb.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private UtilisateurService utilisateurService;


    @Autowired
    private ActiviteTrackerService activiteService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            Utilisateur utilisateur = utilisateurService.trouverParEmail(userDetails.getUsername());
            if (utilisateur != null) {
                activiteService.enregistrerConnexion(utilisateur);
            }
        }
    }
}
