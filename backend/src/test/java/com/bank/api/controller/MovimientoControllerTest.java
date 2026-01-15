package com.bank.api.controller;

import com.bank.api.exception.GlobalExceptionHandler;
import com.bank.api.exception.SaldoNoDisponibleException;
import com.bank.api.service.MovimientoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.annotation.Resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MovimientoController.class, properties = "server.servlet.context-path=/api")
@Import(GlobalExceptionHandler.class)
class MovimientoControllerTest {

    @Resource MockMvc mockMvc;

    @MockitoBean MovimientoService movimientoService;

    @Test
    void shouldReturnSaldoNoDisponible_whenDebitAndNoBalance() throws Exception {
        when(movimientoService.create(any())).thenThrow(new SaldoNoDisponibleException());

        String body = """
        {
          "cuenta": { "id": 1 },
          "tipoMovimiento": "DEPOSITO",
          "valor": 100
        }
        """;

        mockMvc.perform(post("/api/movimientos")
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Saldo no disponible"))
                .andExpect(jsonPath("$.code").value("SALDO_NO_DISPONIBLE"));
    }
}
