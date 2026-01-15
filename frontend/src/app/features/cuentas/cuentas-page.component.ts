import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CuentaService } from '../../core/api/services/cuenta.service';
import { Cuenta } from '../../core/models/cuenta.model';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cuentas-page.component.html',
  styleUrls: ['./cuentas-page.component.css'],
})
export class CuentasPageComponent implements OnInit {
  cuentas: Cuenta[] = [];
  filtered: Cuenta[] = [];
  q = '';
  loading = false;
  error = '';

  constructor(private readonly cuentaService: CuentaService) {}

  ngOnInit(): void {
    this.load();
  }

  private load(): void {
    this.loading = true;
    this.error = '';
    this.cuentaService.list().subscribe({
      next: (data) => {
        this.cuentas = data ?? [];
        this.filtered = [...this.cuentas];
        this.loading = false;
      },
      error: (err) => {
        this.error = err?.error?.message ?? 'Error cargando cuentas';
        this.loading = false;
      },
    });
  }

  applyFilter(): void {
    const term = this.q.trim().toLowerCase();
    if (!term) {
      this.filtered = [...this.cuentas];
      return;
    }
    this.filtered = this.cuentas.filter((c) => {
      const numero = (c.numeroCuenta ?? '').toLowerCase();
      const tipo = (c.tipoCuenta ?? '').toLowerCase();
      const cliente = (c.cliente?.nombre ?? '').toLowerCase();
      const clientId = (c.cliente?.clientId ?? '').toLowerCase();
      return (
        numero.includes(term) ||
        tipo.includes(term) ||
        cliente.includes(term) ||
        clientId.includes(term)
      );
    });
  }
}
