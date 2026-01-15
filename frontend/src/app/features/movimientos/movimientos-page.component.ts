import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { MovimientoService } from '../../core/api/services/movimiento.service';
import { Movimiento } from '../../core/models/movimiento.model';
import { Cuenta } from '../../core/models/cuenta.model';
import { Cliente } from '../../core/models/cliente.model';
import { ClienteService } from '../../core/api/services/cliente.service';
import { DateRangeComponent } from '../../shared/components/date-range/date-range.component';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, DateRangeComponent],
  templateUrl: './movimientos-page.component.html',
  styleUrl: './movimientos-page.component.css',
})
export class MovimientosPageComponent implements OnInit {
  movimientos: Movimiento[] = [];
  filtered: Movimiento[] = [];
  q = '';
  loading = false;
  error = '';

  cuentas: Cuenta[] = [];
  clientes: Cliente[] = [];

  filters: {
    clienteId: number | null;
    fechaInicio: string | null;
    fechaFin: string | null;
  } = {
    clienteId: null,
    fechaInicio: null,
    fechaFin: null,
  };

  dateRangeValid = true;
  dateRangeMessage = '';
  constructor(
    private readonly movimientoService: MovimientoService,
    private readonly clienteService: ClienteService
  ) {}

  ngOnInit(): void {
    this.clienteService
      .list()
      .subscribe({ next: (d) => (this.clientes = d ?? []) });
  }

  applyFilter(): void {
    const term = this.q.trim().toLowerCase();
    if (!term) {
      this.filtered = [...this.movimientos];
      return;
    }

    this.filtered = this.movimientos.filter((m) => {
      const cuenta = (m.cuenta?.numeroCuenta ?? '').toLowerCase();
      const tipo = (m.tipoMovimiento ?? '').toLowerCase();
      const cliente = (m.cuenta?.cliente?.nombre ?? '').toLowerCase();
      const clientId = (m.cuenta?.cliente?.clientId ?? '').toLowerCase();
      return (
        cuenta.includes(term) ||
        tipo.includes(term) ||
        cliente.includes(term) ||
        clientId.includes(term)
      );
    });
  }
  buscar(): void {
    this.error = '';
    this.loading = true;

    if (
      !this.filters.clienteId ||
      !this.filters.fechaInicio ||
      !this.filters.fechaFin ||
      !this.dateRangeValid
    ) {
      this.loading = false;
      this.error =
        'Debes seleccionar cliente y rango de fechas. ' + this.dateRangeMessage;
      this.movimientos = [];
      this.filtered = [];
      return;
    }

    this.movimientoService
      .list({
        clienteId: this.filters.clienteId,
        desde: this.filters.fechaInicio,
        hasta: this.filters.fechaFin,
      })
      .subscribe({
        next: (data) => {
          this.movimientos = data ?? [];
          this.filtered = [...this.movimientos];
          this.loading = false;
        },
        error: (err) => {
          this.error = err?.error?.message ?? 'Error consultando movimientos';
          this.loading = false;
        },
      });
  }

  onDateRangeChange(v: {
    desde: string | null;
    hasta: string | null;
    valid: boolean;
    message: string;
  }) {


    this.filters.fechaInicio = v.desde;
    this.filters.fechaFin = v.hasta;
    this.dateRangeValid = v.valid;
    this.dateRangeMessage = v.message;
  }
}
