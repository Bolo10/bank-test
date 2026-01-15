package com.bank.api.repository;

import com.bank.api.entity.Movimiento;
import com.bank.api.entity.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaIdOrderByFechaDesc(Long cuentaId);

    @Query("""
           SELECT m
           FROM Movimiento m
           JOIN m.cuenta c
           JOIN c.cliente cl
           WHERE cl.id = :clienteId
             AND m.fecha BETWEEN :inicio AND :fin
           ORDER BY m.fecha DESC
           """)
    List<Movimiento> findByClienteAndRangoFechas(Long clienteId, LocalDate inicio, LocalDate fin);

    // Suma de débitos en un rango (para cupo diario)
    // Si guardas RETIRO como valor negativo, esta suma será negativa.
    // En el service: totalRetiro = sum == null ? 0 : sum.negate()
    // no olvidar que coalesce para evitar null
    @Query("""
           SELECT COALESCE(SUM(m.valor), 0)
           FROM Movimiento m
           WHERE m.cuenta.id = :cuentaId
             AND m.tipoMovimiento = :tipo
             AND m.fecha BETWEEN :inicio AND :fin
           """)
    BigDecimal sumValorByCuentaAndTipoAndRango(Long cuentaId, TipoMovimiento tipo, LocalDate inicio, LocalDate fin);
}
