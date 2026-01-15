import { Cuenta } from './cuenta.model';

export type TipoMovimiento = 'RETIRO' | 'DEPOSITO';

export interface Movimiento {
  id: number;
  fecha: string;
  tipoMovimiento: TipoMovimiento;
  valor: number;
  saldo: number;
  cuenta: Cuenta;
}

export interface MovimientoCreateDto {
  cuentaId: number;
  tipoMovimiento: TipoMovimiento;
  valor: number;
  fecha?: string;
}
