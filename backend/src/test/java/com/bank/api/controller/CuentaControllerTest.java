package com.bank.api.controller;

import com.bank.api.entity.Cliente;
import com.bank.api.entity.Cuenta;
import com.bank.api.entity.TipoCuenta;
import com.bank.api.exception.GlobalExceptionHandler;
import com.bank.api.service.CuentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = CuentaController.class, properties = "server.servlet.context-path=/api")
@Import(GlobalExceptionHandler.class)
class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    
    @MockitoBean
    private CuentaService cuentaService;

    @Test
    @DisplayName("GET /cuentas debe retornar lista de cuentas")
    void list_debeRetornarLista() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(10L);
        Cuenta c1 = new Cuenta(); c1.setId(1L); c1.setNumeroCuenta("10001"); c1.setTipoCuenta(TipoCuenta.AHORROS); c1.setSaldoInicial(new BigDecimal("500.00")); c1.setEstado(true); c1.setCliente(cliente);
        Cuenta c2 = new Cuenta(); c2.setId(2L); c2.setNumeroCuenta("10002"); c2.setTipoCuenta(TipoCuenta.CORRIENTE); c2.setSaldoInicial(new BigDecimal("1000.00")); c2.setEstado(true); c2.setCliente(cliente);

        when(cuentaService.list(ArgumentMatchers.any()))
                .thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/cuentas")
                        .param("q", "") 
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].numeroCuenta").value("10001"))
                .andExpect(jsonPath("$[1].tipoCuenta").value("CORRIENTE"));
    }

@Test
@DisplayName("POST /cuentas debe crear cuenta y retornar 201/200 con JSON")
void create_debeCrearCuenta() throws Exception {
    
    
    Cliente cliente = new Cliente();
    cliente.setId(10L);

    Cuenta req = new Cuenta();
    req.setNumeroCuenta("10099");
    req.setTipoCuenta(TipoCuenta.AHORROS);
    req.setSaldoInicial(new BigDecimal("250.00"));
    req.setEstado(true);
    req.setCliente(cliente);

    Cuenta resp = new Cuenta();
    resp.setId(99L);
    resp.setNumeroCuenta("10099");
    resp.setTipoCuenta(TipoCuenta.AHORROS);
    resp.setSaldoInicial(new BigDecimal("250.00"));
    resp.setEstado(true);
    resp.setCliente(cliente);

    when(cuentaService.create(ArgumentMatchers.any(Cuenta.class)))
            .thenReturn(resp);

    
    mockMvc.perform(post("/cuentas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(99))
            .andExpect(jsonPath("$.numeroCuenta").value("10099"))
            .andExpect(jsonPath("$.cliente.id").value(10));
}

}
