package com.bank.api.service.impl;

import com.bank.api.dto.response.ReporteCuentaResponse;
import com.bank.api.dto.response.ReporteResponse;
import com.bank.api.entity.Cliente;
import com.bank.api.entity.Cuenta;
import com.bank.api.entity.Movimiento;
import com.bank.api.entity.TipoMovimiento;
import com.bank.api.exception.NotFoundException;
import com.bank.api.repository.ClienteRepository;
import com.bank.api.repository.CuentaRepository;
import com.bank.api.repository.MovimientoRepository;
import com.bank.api.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    @Override
    public ReporteResponse generar(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        System.out.println("clienteId: " + clienteId + ", fechaInicio: " + fechaInicio + ", fechaFin: " + fechaFin);


        //Cliente cliente = clienteRepository.findById(clienteId)
        //   


        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(23, 59, 59);

        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);
        List<Movimiento> movimientos = movimientoRepository.findByClienteAndRangoFechas(clienteId, inicio, fin);

        // Agrupar movimientos por cuenta
        Map<Long, List<Movimiento>> movsPorCuenta = new HashMap<>();
        movimientos.forEach(m -> movsPorCuenta
                .computeIfAbsent(m.getCuenta().getId(), k -> new ArrayList<>())
                .add(m));

        List<ReporteCuentaResponse> cuentasResp = cuentas.stream().map(c -> {
            List<Movimiento> movs = movsPorCuenta.getOrDefault(c.getId(), List.of());

            BigDecimal totalCreditos = movs.stream()
                    .filter(m -> m.getTipoMovimiento() == TipoMovimiento.CREDITO)
                    .map(Movimiento::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // si tus débitos son negativos, totalDebitos será negativo. Lo pasamos a positivo:
            BigDecimal totalDebitos = movs.stream()
                    .filter(m -> m.getTipoMovimiento() == TipoMovimiento.DEBITO)
                    .map(Movimiento::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .abs();

            return new ReporteCuentaResponse(
                    c.getId(),
                    c.getNumeroCuenta(),
                    c.getTipoCuenta().name(),
                    c.getSaldoInicial(),         // saldo actual (lo vienes actualizando en movimientos)
                    totalDebitos,
                    totalCreditos
            );
        }).toList();

        byte[] pdfBytes = PdfFactory.build(cliente, fechaInicio, fechaFin, cuentasResp);
        String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);

        return new ReporteResponse(
                cliente.getId(),
                cliente.getClientId(),
                cliente.getNombre(),
                fechaInicio.toString(),
                fechaFin.toString(),
                cuentasResp,
                pdfBase64
        );
    }
}
