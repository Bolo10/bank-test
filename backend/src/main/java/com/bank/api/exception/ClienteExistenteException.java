package com.bank.api.exception;

public class ClienteExistenteException extends RuntimeException {
    public ClienteExistenteException() {
        super("Cliente existente con esa identificación");
    }
    
}
