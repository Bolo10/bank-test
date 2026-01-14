package com.bank.api.service.impl;

import com.bank.api.entity.Cuenta;
import com.bank.api.exception.CuentaExistenteException;
import com.bank.api.exception.NotFoundException;
import com.bank.api.repository.CuentaRepository;
import com.bank.api.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository repository;

    @Override
    public Cuenta create(Cuenta cuenta) {
        repository.findByNumeroCuenta(cuenta.getNumeroCuenta())
        .ifPresent(c -> {
            throw new CuentaExistenteException();
        });
        return repository.save(cuenta);
    }

    @Override
    public Cuenta getById(Long id) {
        System.out.println("entraaaaaaaa");
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
    }

    @Override
    public List<Cuenta> list(Long clienteId) {
        if (clienteId == null) {
            return repository.findByEstadoTrue();
        }
        return repository.findByClienteId(clienteId);
    }

    @Override
    public Cuenta update(Long id, Cuenta cuenta) {
        Cuenta existing = getById(id);
        cuenta.setId(existing.getId());
        return repository.save(cuenta);
    }

    @Override
    public Cuenta patch(Long id, Cuenta partial) {
        Cuenta cuenta = getById(id);

        if (partial.getEstado() != null) cuenta.setEstado(partial.getEstado());
        if (partial.getTipoCuenta() != null) cuenta.setTipoCuenta(partial.getTipoCuenta());

        return repository.save(cuenta);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Cuenta no encontrada");
        }
        Cuenta cuenta = getById(id);
        cuenta.setEstado(false);
        repository.save(cuenta);
    }
}
