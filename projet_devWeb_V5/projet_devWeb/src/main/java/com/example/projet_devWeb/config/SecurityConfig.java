package com.example.projet_devWeb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @Autowired
    private UtilisateurDetailsService utilisateurDetailsService;

    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", 
                    "/connexion", 
                    "/inscription", 
                    "/verification", 
                    "/mot-de-passe-oublie", 
                    "/reinitialisation", 
                    "/accueil", 
                    "/style.css"
                ).permitAll()

                .requestMatchers("/static/**", "/images/**", "/css/**", "/js/**").permitAll() //Acces aux documents dans static et images
                 .requestMatchers(HttpMethod.GET, "/**").permitAll() // Permet l'accès aux pages GET
                .requestMatchers("/utilisateur").hasAuthority("ADMINISTRATEUR")
                .requestMatchers("/profil").authenticated()

                .anyRequest().authenticated() // ✅ TOUJOURS EN DERNIER
            )
            .formLogin(form -> form
                .loginPage("/connexion")
                .loginProcessingUrl("/connexion")
                .successHandler(customLoginSuccessHandler)
                .failureHandler(customFailureHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/connexion?logout") // Redirige après déconnexion
                .permitAll()
            )
            .exceptionHandling(exception -> 
                exception.accessDeniedPage("/acces-refuse")
            );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(utilisateurDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
