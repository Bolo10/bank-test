package com.bank.api.controller;

import com.bank.api.entity.Movimiento;
import com.bank.api.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movimiento create(@Valid @RequestBody Movimiento movimiento) {
        return service.create(movimiento);
    }

    @GetMapping("/{id}")
    public Movimiento getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // /movimientos?cuentaId=1
    @GetMapping(params = "cuentaId")
    public List<Movimiento> listByCuenta(@RequestParam Long cuentaId) {
        return service.listByCuenta(cuentaId);
    }

    // /movimientos?clienteId=1&fechaInicio=2026-01-01&fechaFin=2026-01-12
    @GetMapping(params = {"clienteId", "fechaInicio", "fechaFin"})
    public List<Movimiento> listByClienteAndRango(
            @RequestParam Long clienteId,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin
    ) {
        
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        
        return service.listByClienteAndRango(clienteId, inicio, fin);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        //HACER ELIMINADO LOGICO
        service.delete(id);

    }
}
