package com.bank.api.service;

import com.bank.api.entity.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente create(Cliente cliente);
    Cliente getById(Long id);
    List<Cliente> list(String q);
    Cliente update(Long id, Cliente cliente);     // PUT
    Cliente patch(Long id, Cliente partial);      // PATCH (simple)
    void delete(Long id);
}
