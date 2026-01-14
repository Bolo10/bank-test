import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Cuenta, CuentaCreateDto, CuentaUpdateDto } from '../models/cuenta.model';

@Injectable({ providedIn: 'root' })
export class CuentaService {
  private readonly baseUrl = '/api/cuentas';

  constructor(private readonly http: HttpClient) {}

  list(): Observable<Cuenta[]> {
    return this.http.get<Cuenta[]>(this.baseUrl);
  }

  getById(id: number): Observable<Cuenta> {
    return this.http.get<Cuenta>(`${this.baseUrl}/${id}`);
  }

  create(payload: CuentaCreateDto): Observable<Cuenta> {

    const body = {
      numeroCuenta: payload.numeroCuenta,
      tipoCuenta: payload.tipoCuenta,
      saldoInicial: payload.saldoInicial,
      estado: payload.estado,
      cliente: { id: payload.clienteId },
    };
    return this.http.post<Cuenta>(this.baseUrl, body);
  }

  update(id: number, payload: CuentaUpdateDto): Observable<Cuenta> {
    const body: any = {
      ...payload,
      ...(payload.clienteId ? { cliente: { id: payload.clienteId } } : {}),
    };
    delete body.clienteId;
    return this.http.put<Cuenta>(`${this.baseUrl}/${id}`, body);
  }

  patch(id: number, payload: CuentaUpdateDto): Observable<Cuenta> {
    const body: any = {
      ...payload,
      ...(payload.clienteId ? { cliente: { id: payload.clienteId } } : {}),
    };
    delete body.clienteId;
    return this.http.patch<Cuenta>(`${this.baseUrl}/${id}`, body);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
