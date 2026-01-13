package com.bank.api.controller;

import com.bank.api.entity.Cuenta;
import com.bank.api.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cuenta create(@Valid @RequestBody Cuenta cuenta) {
        return service.create(cuenta);
    }

    @GetMapping("/{id}")
    public Cuenta getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // /cuentas?clienteId=1
    @GetMapping
    public List<Cuenta> list(@RequestParam(name = "clienteId", required = false) Long clienteId) {
        System.out.println("entra");
        return service.list(clienteId);
    }

    @PutMapping("/{id}")
    public Cuenta update(@PathVariable Long id, @Valid @RequestBody Cuenta cuenta) {
        return service.update(id, cuenta);
    }

    @PatchMapping("/{id}")
    public Cuenta patch(@PathVariable Long id, @RequestBody Cuenta partial) {
        return service.patch(id, partial);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        System.out.println("recordar que hay que hacer eliminado logico");
        service.delete(id);
    }
}
