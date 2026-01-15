package com.bank.api.service.impl;

import com.bank.api.entity.*;
import com.bank.api.exception.CupoDiarioExcedidoException;
import com.bank.api.exception.NotFoundException;
import com.bank.api.exception.SaldoNoDisponibleException;
import com.bank.api.repository.CuentaRepository;
import com.bank.api.repository.MovimientoRepository;
import com.bank.api.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private static final BigDecimal LIMITE_DIARIO = new BigDecimal("1000");

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    @Override
    @Transactional
    public Movimiento create(Movimiento movimiento) {

        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getId())
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));

        BigDecimal saldoActual = cuenta.getSaldoInicial();
        BigDecimal valor = movimiento.getValor();

        if (movimiento.getTipoMovimiento() == TipoMovimiento.RETIRO) {

            if (saldoActual.compareTo(BigDecimal.ZERO) <= 0 || saldoActual.add(valor).compareTo(BigDecimal.ZERO) < 0) {
                throw new SaldoNoDisponibleException();
            }

            validarCupoDiario(cuenta.getId(), valor);
        }

        BigDecimal nuevoSaldo = saldoActual.add(valor);

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);
        System.out.println(LocalDateTime.now());
        movimiento.setFecha(LocalDate.now());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);

        return movimientoRepository.save(movimiento);
    }

    private void validarCupoDiario(Long cuentaId, BigDecimal valor) {
        LocalDate hoy = LocalDate.now();
        LocalDate inicio = hoy;
        LocalDate fin = hoy;
        
        BigDecimal totalHoy = movimientoRepository
                .sumValorByCuentaAndTipoAndRango(cuentaId, TipoMovimiento.RETIRO, inicio, fin)
                .abs(); // porque son negativos

        BigDecimal intento = valor.abs();

        if (totalHoy.add(intento).compareTo(LIMITE_DIARIO) > 0) {
            throw new CupoDiarioExcedidoException();
        }
    }

    @Override
    public Movimiento getById(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movimiento no encontrado"));
    }

    @Override
    public List<Movimiento> listByCuenta(Long cuentaId) {
        return movimientoRepository.findByCuentaIdOrderByFechaDesc(cuentaId);
    }

    @Override
    public List<Movimiento> listByClienteAndRango(Long clienteId, LocalDate inicio, LocalDate fin) {
        return movimientoRepository.findByClienteAndRangoFechas(clienteId, inicio, fin);
    }

    @Override
    public void delete(Long id) {
        if (!movimientoRepository.existsById(id)) {
            throw new NotFoundException("Movimiento no encontrado");
        }
        movimientoRepository.deleteById(id);
    }
}
