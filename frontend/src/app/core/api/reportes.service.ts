import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ReporteResponse {

  data: any;
  pdfBase64: string;
}

@Injectable({ providedIn: 'root' })
export class ReporteService {
  private readonly baseUrl = '/api/reportes';

  constructor(private readonly http: HttpClient) {}

  getEstadoCuenta(paramsIn: {
    clienteId: number;
    fechaInicio: string;
    fechaFin: string;
  }): Observable<ReporteResponse> {
    let params = new HttpParams()
      .set('clienteId', String(paramsIn.clienteId))
      .set('fechaInicio', paramsIn.fechaInicio)
      .set('fechaFin', paramsIn.fechaFin);

    return this.http.get<ReporteResponse>(this.baseUrl, { params });
  }
}
