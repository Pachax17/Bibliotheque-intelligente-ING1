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
                    "/agenda" ,
                    "/itineraire",
                    "/agenda.css", 
                    "/style.css", 
                    "/Objets",             // autorise /Objets
                    "/Objets/**",          // autorise aussi /Objets/quelquechose
                    "/static/**", 
                    "/images/**", 
                    "/css/**", 
                    "/js/**"
                ).permitAll()

                .requestMatchers("/utilisateur").hasAuthority("ADMINISTRATEUR")
                .requestMatchers("/profil").authenticated()
                .requestMatchers("/objets/proposer").hasAuthority("AVANCE")
                .requestMatchers("/administrateur/**").hasAuthority("ADMINISTRATEUR")

                .anyRequest().authenticated() // ✅ Toujours en dernier
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
                .logoutSuccessUrl("/connexion?logout")
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
