package com.bank.api.dto.response;

import java.util.List;

public record ReporteResponse(
        Long clienteId,
        String clientId,
        String nombre,
        String fechaInicio,
        String fechaFin,
        List<ReporteCuentaResponse> cuentas,
        String pdfBase64
) {}
