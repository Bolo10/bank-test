import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MovimientoService } from '../../../core/api/movimiento.service';
import { CuentaService } from '../../../core/api/cuenta.service';
import { Cuenta } from '../../../core/models/cuenta.model';
import { TipoMovimiento } from '../../../core/models/movimiento.model';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './movimientos-form-page.component.html',
  styleUrls: ['./movimientos-form-page.component.css'],
})
export class MovimientoFormPageComponent implements OnInit {
  cuentas: Cuenta[] = [];
  saving = false;
  error = '';

  model: {
    cuentaId: number | null;
    tipoMovimiento: TipoMovimiento | '';
    valor: number | null;
    fecha: string; // yyyy-mm-dd
  } = {
    cuentaId: null,
    tipoMovimiento: '',
    valor: null,
    fecha: new Date().toISOString().slice(0, 10),
  };

  constructor(
    private readonly router: Router,
    private readonly movimientoService: MovimientoService,
    private readonly cuentaService: CuentaService
  ) {}

  ngOnInit(): void {
    this.cuentaService.list().subscribe({
      next: (data) => {
        this.cuentas = data ?? [];
      },
      error: () => {
        this.error = 'No se pudieron cargar las cuentas';
      },
    });
  }

  signedValor(): number {
    const v = Math.abs(Number(this.model.valor ?? 0));
    return this.model.tipoMovimiento === 'DEBITO' ? -v : v;
  }

  onSubmit(f: NgForm): void {
    this.error = '';
    if (f.invalid) {
      this.error = 'Revisa los campos requeridos.';
      return;
    }

    this.saving = true;

    this.movimientoService
      .create({
        cuentaId: this.model.cuentaId!,
        tipoMovimiento: this.model.tipoMovimiento as TipoMovimiento,
        valor: this.signedValor(),
      })
      .subscribe({
        next: () => {
          this.saving = false;
          this.router.navigate(['/movimientos']);
        },
        error: (err) => {
          this.saving = false;
          // aquí se verán: "Saldo no disponible" / "Cupo diario excedido"
          this.error = err?.error?.message ?? 'Error al crear movimiento';
        },
      });
  }
}
