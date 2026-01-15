import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CuentaService } from '../../../core/api/services/cuenta.service';
import { ClienteService } from '../../../core/api/services/cliente.service';
import { Cliente } from '../../../core/models/cliente.model';
import { TipoCuenta } from '../../../core/models/cuenta.model';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cuentas-form-page.component.html',
  styleUrls: ['./cuentas-form-page.component.css'],
})
export class CuentaFormPageComponent implements OnInit {
  isEdit = false;
  id: number | null = null;

  clientes: Cliente[] = [];

  saving = false;
  error = '';

  model: {
    numeroCuenta: string;
    tipoCuenta: TipoCuenta | '';
    saldoInicial: number | null;
    estado: boolean;
    clienteId: number | null;
  } = {
    numeroCuenta: '',
    tipoCuenta: '',
    saldoInicial: null,
    estado: true,
    clienteId: null,
  };

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly cuentaService: CuentaService,
    private readonly clienteService: ClienteService
  ) {}

  ngOnInit(): void {
    this.loadClientes();

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEdit = true;
      this.id = Number(idParam);
      this.loadCuenta(this.id);
    }
  }

  private loadClientes(): void {
    this.clienteService.list().subscribe({
      next: (data) => {
        this.clientes = data ?? [];
      },
      error: () => {
        this.error = 'No se pudieron cargar los clientes';
      },
    });
  }

  private loadCuenta(id: number): void {
    this.cuentaService.getById(id).subscribe({
      next: (c) => {
        this.model.numeroCuenta = c.numeroCuenta ?? '';
        this.model.tipoCuenta = (c.tipoCuenta as any) ?? '';
        this.model.saldoInicial = c.saldoInicial ?? null;
        this.model.estado = !!c.estado;
        this.model.clienteId = c.cliente?.id ?? null;
      },
      error: (err) => {
        this.error = err?.error?.message ?? 'No se pudo cargar la cuenta';
      },
    });
  }

  onSubmit(f: NgForm): void {
    this.error = '';
    if (f.invalid) {
      this.error = 'Revisa los campos requeridos.';
      return;
    }

    this.saving = true;

    const payload = {
      numeroCuenta: this.model.numeroCuenta.trim(),
      tipoCuenta: this.model.tipoCuenta as TipoCuenta,
      saldoInicial: Number(this.model.saldoInicial),
      estado: this.model.estado,
      clienteId: this.model.clienteId!,
    };

    if (!this.isEdit) {
      this.cuentaService.create(payload).subscribe({
        next: () => {
          this.saving = false;
          this.router.navigate(['/cuentas']);
        },
        error: (err) => {
          this.saving = false;
          this.error = err?.error?.message ?? 'Error al crear cuenta';
        },
      });
      return;
    }

    this.cuentaService.update(this.id!, payload).subscribe({
      next: () => {
        this.saving = false;
        this.router.navigate(['/cuentas']);
      },
      error: (err) => {
        this.saving = false;
        this.error = err?.error?.message ?? 'Error al actualizar cuenta';
      },
    });
  }
}
