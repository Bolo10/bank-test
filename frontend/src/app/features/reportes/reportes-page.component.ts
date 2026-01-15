import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ClienteService } from '../../core/api/services/cliente.service';
import {
  ReporteService,
  ReporteResponse,
} from '../../core/api/services/reportes.service';
import { Cliente } from '../../core/models/cliente.model';
import { DateRangeComponent } from '../../shared/components/date-range/date-range.component';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, DateRangeComponent],
  templateUrl: './reportes-page.component.html',
  styleUrls: ['./reportes-page.component.css'],
})
export class ReportesPageComponent implements OnInit {
  clientes: Cliente[] = [];

  form: { clienteId: number | null; fechaInicio: string; fechaFin: string } = {
    clienteId: null,
    fechaInicio: new Date().toISOString().slice(0, 10),
    fechaFin: new Date().toISOString().slice(0, 10),
  };

  loading = false;
  error = '';

  reporte: any = null;
  pdfBase64 = '';

  totals = { creditos: 0, debitos: 0, saldoFinal: 0 };

  dateRangeValid = false;
  dateRangeMessage = '';

  constructor(
    private readonly clienteService: ClienteService,
    private readonly reporteService: ReporteService
  ) {}

  ngOnInit(): void {
    this.clienteService
      .list()
      .subscribe({ next: (d) => (this.clientes = d ?? []) });
  }

  buscar(f: NgForm): void {
    this.error = '';
    this.reporte = null;
    this.pdfBase64 = '';
    this.totals = { creditos: 0, debitos: 0, saldoFinal: 0 };

    if (f.invalid || !this.dateRangeValid) {
      this.error =
        'Selecciona cliente y rango de fechas. ' + this.dateRangeMessage;
      return;
    }

    this.loading = true;

    this.reporteService
      .getEstadoCuenta({
        clienteId: this.form.clienteId!,
        fechaInicio: this.form.fechaInicio,
        fechaFin: this.form.fechaFin,
      })
      .subscribe({
        next: (res: ReporteResponse) => {
          this.loading = false;

          // Ajusta si tu backend usa otros nombres:
          this.reporte = res;
          this.pdfBase64 = res.pdfBase64 || '';

          this.computeTotalsAndBars(this.reporte);
        },
        error: (err) => {
          this.loading = false;
          this.error = err?.error?.message ?? 'Error generando reporte';
        },
      });
  }

  downloadPdf(): void {
    if (!this.pdfBase64) return;

    const byteCharacters = atob(this.pdfBase64);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++)
      byteNumbers[i] = byteCharacters.charCodeAt(i);

    const blob = new Blob([new Uint8Array(byteNumbers)], {
      type: 'application/pdf',
    });
    const url = URL.createObjectURL(blob);

    const a = document.createElement('a');
    a.href = url;
    a.download = `reporte_${this.form.clienteId}_${this.form.fechaInicio}_${this.form.fechaFin}.pdf`;
    a.click();

    URL.revokeObjectURL(url);
  }

  private computeTotalsAndBars(data: any): void {
    const cuentas = Array.isArray(data?.cuentas) ? data.cuentas : [];

    const creditos = cuentas.reduce(
      (acc: number, c: any) => acc + Number(c.totalCreditos ?? 0),
      0
    );
    const debitos = cuentas.reduce(
      (acc: number, c: any) => acc + Number(c.totalDebitos ?? 0),
      0
    );

    // saldo final: suma saldos actuales o si prefieres mostrar por cuenta, deja 0 y lo pintas en tabla
    const saldoFinal = cuentas.reduce(
      (acc: number, c: any) => acc + Number(c.saldoActual ?? 0),
      0
    );

    this.totals = { creditos, debitos, saldoFinal };



  }

  onDateRangeChange(v: {
    desde: string | null;
    hasta: string | null;
    valid: boolean;
    message: string;
  }) {
    
    let desde = new Date(v.desde!).toISOString().slice(0, 10);
    let hasta = new Date(v.hasta!).toISOString().slice(0, 10);

    this.form.fechaInicio = desde;
    this.form.fechaFin = hasta;
    this.dateRangeValid = v.valid;
    this.dateRangeMessage = v.message;
  }
}
