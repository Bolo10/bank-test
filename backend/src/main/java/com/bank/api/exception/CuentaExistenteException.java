package com.bank.api.exception;

public class CuentaExistenteException extends RuntimeException {
    public CuentaExistenteException() {
        super("Número de cuenta ya existente");
    }
}
