package com.example.projet_devWeb.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String redirectUrl = "/connexion?error";

        if ("NON_VERIFIE".equals(exception.getMessage())) {
            redirectUrl = "/connexion?nonVerifie";
        }

        response.sendRedirect(redirectUrl);
    }
}
