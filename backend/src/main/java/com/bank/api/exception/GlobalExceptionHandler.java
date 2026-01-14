package com.bank.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getMessage(), "NOT_FOUND", Instant.now(), req.getRequestURI()));
    }

    @ExceptionHandler(SaldoNoDisponibleException.class)
    public ResponseEntity<ApiError> handleSaldo(SaldoNoDisponibleException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(ex.getMessage(), "SALDO_NO_DISPONIBLE", Instant.now(), req.getRequestURI()));
    }

    @ExceptionHandler(CupoDiarioExcedidoException.class)
    public ResponseEntity<ApiError> handleCupo(CupoDiarioExcedidoException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(ex.getMessage(), "CUPO_DIARIO_EXCEDIDO", Instant.now(), req.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        var msg = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .orElse("Validation error");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(msg, "VALIDATION_ERROR", Instant.now(), req.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
        //PRINT PARA VERIFICAR que entra al Excemption Handler
        System.out.println("Entra al exception: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("Error interno", "INTERNAL_ERROR", Instant.now(), req.getRequestURI()));
    }

    @ExceptionHandler(ClienteExistenteException.class)
    public ResponseEntity<ApiError> handleClienteExistente(ClienteExistenteException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(ex.getMessage(), "CLIENTE_EXISTENTE", Instant.now(), req.getRequestURI()));
    }

    @ExceptionHandler(CuentaExistenteException.class)
    public ResponseEntity<ApiError> handleCuentaExistente(CuentaExistenteException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(ex.getMessage(), "CUENTA_EXISTENTE", Instant.now(), req.getRequestURI()));
    }
}
