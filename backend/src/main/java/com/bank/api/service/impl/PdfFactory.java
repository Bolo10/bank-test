package com.bank.api.service.impl;

import com.bank.api.dto.response.ReporteCuentaResponse;
import com.bank.api.entity.Cliente;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

final class PdfFactory {

    private PdfFactory() {}

    static byte[] build(Cliente cliente, LocalDate inicio, LocalDate fin, List<ReporteCuentaResponse> cuentas) {
        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                float y = 750;

                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                cs.beginText(); cs.newLineAtOffset(50, y);
                cs.showText("Estado de Cuenta");
                cs.endText();

                y -= 25;
                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 11);
                writeLine(cs, 50, y, "Cliente: " + cliente.getNombre() + " (" + cliente.getClientId() + ")");
                y -= 15;
                writeLine(cs, 50, y, "Rango: " + inicio + " a " + fin);

                y -= 25;
                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 11);
                writeLine(cs, 50, y, "Cuentas");
                y -= 15;

                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
                for (ReporteCuentaResponse c : cuentas) {
                    writeLine(cs, 50, y, "Cuenta: " + c.numeroCuenta() + " | Tipo: " + c.tipoCuenta());
                    y -= 13;
                    writeLine(cs, 70, y, "Saldo: " + c.saldoActual()
                            + " | Retiros: " + c.totalDebitos()
                            + " | Depositos: " + c.totalCreditos());
                    y -= 18;

                    if (y < 80) break; //sin paginacion por ahora
                }
            }

            doc.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            // si falla el PDF
            return new byte[0];
        }
    }

    private static void writeLine(PDPageContentStream cs, float x, float y, String text) throws Exception {
        cs.beginText();
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
    }
}
