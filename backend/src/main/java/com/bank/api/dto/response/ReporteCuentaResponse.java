package com.bank.api.dto.response;

import java.math.BigDecimal;

public record ReporteCuentaResponse(
        Long cuentaId,
        String numeroCuenta,
        String tipoCuenta,
        BigDecimal saldoActual,
        BigDecimal totalDebitos,
        BigDecimal totalCreditos
) {}
