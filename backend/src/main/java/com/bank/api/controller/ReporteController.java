package com.bank.api.controller;

import com.bank.api.dto.response.ReporteResponse;
import com.bank.api.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    // /reportes?fechaInicio=2026-01-01&fechaFin=2026-01-12&clienteId=1
    @GetMapping
    public ReporteResponse getReporte(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam Long clienteId
    ) {
        System.out.println("entra");
        return reporteService.generar(
                clienteId,
                LocalDate.parse(fechaInicio),
                LocalDate.parse(fechaFin)
        );
    }
}
