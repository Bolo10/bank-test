package com.bank.api.controller;

import com.bank.api.entity.Cliente;
import com.bank.api.exception.GlobalExceptionHandler;
import com.bank.api.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.annotation.Resource;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClienteController.class, properties = "server.servlet.context-path=/api")
@Import(GlobalExceptionHandler.class)
class ClienteControllerTest {

    @Resource MockMvc mockMvc;
    @Resource ObjectMapper objectMapper;

    @MockitoBean ClienteService clienteService;

    @Test
    void shouldCreateCliente() throws Exception {
        Cliente req = new Cliente();
        req.setNombre("Jose Lema");
        req.setGenero("M");
        req.setEdad(35);
        req.setIdentificacion("1102233445");
        req.setDireccion("Otavalo sn y principal");
        req.setTelefono("098254785");
        req.setPassword("1234");
        req.setEstado(true);

        Cliente resp = new Cliente();
        resp.setId(1L);
        resp.setNombre(req.getNombre());
        resp.setClientId("CL-000001");

        when(clienteService.create(any(Cliente.class))).thenReturn(resp);

        mockMvc.perform(post("/api/clientes")
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.clientId").value("CL-000001"));
    }

    //@Test
    //void shouldListClientes() throws Exception {
    //    Cliente c1 = new Cliente(); c1.setId(1L); c1.setClientId("CL-000001");
    //    Cliente c2 = new Cliente(); c2.setId(2L); c2.setClientId("CL-000002");
//
    //    when(clienteService.list(null)).thenReturn(List.of(c1, c2));
//
    //    mockMvc.perform(get("/api/clientes"))
    //            .andExpect(status().isOk())
    //            .andExpect(jsonPath("$[0].id").value(1))
    //            .andExpect(jsonPath("$[1].id").value(2));
    //}
}
