package com.example.projet_devWeb.config;

import com.example.projet_devWeb.model.Utilisateur;
import com.example.projet_devWeb.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l’email : " + email));

        if (!utilisateur.isVerifie()) {
            throw new CompteNonVerifieException("NON_VERIFIE");
        }

        return new UtilisateurDetails(utilisateur);
    }
}
