package com.bank.api.exception;

public class CupoDiarioExcedidoException extends RuntimeException {
    public CupoDiarioExcedidoException() { super("Cupo diario excedido"); }
}
