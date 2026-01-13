package com.bank.api.service;

import com.bank.api.entity.Cuenta;

import java.util.List;

public interface CuentaService {
    Cuenta create(Cuenta cuenta);
    Cuenta getById(Long id);
    List<Cuenta> list(Long clienteId);
    Cuenta update(Long id, Cuenta cuenta);
    Cuenta patch(Long id, Cuenta partial);
    void delete(Long id);
}
