package com.bank.api.exception;

public class SaldoNoDisponibleException extends RuntimeException {
    public SaldoNoDisponibleException() { super("Saldo no disponible"); }
}
