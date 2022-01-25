package com.example.rimsystem.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @auther luyu
 */
public class CapthcaException extends AuthenticationException {
    public CapthcaException(String msg) {
        super(msg);
    }
}
