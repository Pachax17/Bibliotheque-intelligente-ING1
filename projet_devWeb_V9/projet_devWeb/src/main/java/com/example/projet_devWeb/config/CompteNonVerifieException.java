package com.example.projet_devWeb.config;

import org.springframework.security.core.AuthenticationException;

public class CompteNonVerifieException extends AuthenticationException {
    public CompteNonVerifieException(String msg) {
        super(msg);
    }
}
