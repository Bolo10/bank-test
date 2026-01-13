package com.bank.api.service;

import com.bank.api.dto.response.ReporteResponse;

import java.time.LocalDate;

public interface ReporteService {
    ReporteResponse generar(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin);
}
