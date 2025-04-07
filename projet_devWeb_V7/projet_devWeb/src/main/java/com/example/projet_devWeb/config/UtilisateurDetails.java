package com.example.projet_devWeb.config;

import com.example.projet_devWeb.model.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UtilisateurDetails implements UserDetails {

    private final Utilisateur utilisateur;

    public UtilisateurDetails(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 🔐 On utilise le nom du rôle (enum -> string) comme autorité
        return Collections.singletonList(new SimpleGrantedAuthority(utilisateur.getRole().name()));
    }

    @Override
    public String getPassword() {
        return utilisateur.getMotDePasse();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail(); // Authentification via email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Toujours actif
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Toujours non bloqué
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Toujours valides
    }

    @Override
    public boolean isEnabled() {
        return utilisateur.isVerifie(); // ✅ Actif seulement si vérifié par email
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
}
