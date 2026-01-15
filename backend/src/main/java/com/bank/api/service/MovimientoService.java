package com.bank.api.service;

import com.bank.api.entity.Movimiento;

import java.time.LocalDate;

import java.util.List;

public interface MovimientoService {
    Movimiento create(Movimiento movimiento);
    Movimiento getById(Long id);
    List<Movimiento> listByCuenta(Long cuentaId);
    List<Movimiento> listByClienteAndRango(Long clienteId, LocalDate inicio, LocalDate fin);
    void delete(Long id);
}
