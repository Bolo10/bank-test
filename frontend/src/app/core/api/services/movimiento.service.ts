import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movimiento, MovimientoCreateDto } from '../../models/movimiento.model';

@Injectable({ providedIn: 'root' })
export class MovimientoService {
  private readonly baseUrl = '/api/movimientos';

  constructor(private readonly http: HttpClient) {}

  list(filters?: {
    clienteId?: number;
    desde?: string;
    hasta?: string;
  }): Observable<Movimiento[]> {
    console.log(filters);
    let params = new HttpParams();

    if (filters?.clienteId)
      params = params.set('clienteId', String(filters.clienteId));
    if (filters?.desde) params = params.set('fechaInicio', filters.desde);
    if (filters?.hasta) params = params.set('fechaFin', filters.hasta);

    return this.http.get<Movimiento[]>(this.baseUrl, { params });
  }

  create(payload: MovimientoCreateDto): Observable<Movimiento> {
    const body: any = {
      tipoMovimiento: payload.tipoMovimiento,
      valor: payload.valor,
      cuenta: { id: payload.cuentaId },
    };
    if (payload.fecha) body.fecha = payload.fecha;

    return this.http.post<Movimiento>(this.baseUrl, body);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
