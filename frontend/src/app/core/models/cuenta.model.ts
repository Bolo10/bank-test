import { Cliente } from './cliente.model';

export type TipoCuenta = 'AHORROS' | 'CORRIENTE';

export interface Cuenta {
  id: number;
  numeroCuenta: string;
  tipoCuenta: TipoCuenta;
  saldoInicial: number;
  estado: boolean;
  cliente: Cliente;
}

export interface CuentaCreateDto {
  numeroCuenta: string;
  tipoCuenta: TipoCuenta;
  saldoInicial: number;
  estado: boolean;
  clienteId: number;
}

export interface CuentaUpdateDto {
  numeroCuenta?: string;
  tipoCuenta?: TipoCuenta;
  saldoInicial?: number;
  estado?: boolean;
  clienteId?: number;
}
