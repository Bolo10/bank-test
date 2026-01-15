import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente, ClienteCreateDto, ClienteUpdateDto } from '../../models/cliente.model';

@Injectable({ providedIn: 'root' })
export class ClienteService {
  private readonly baseUrl = '/api/clientes';

  constructor(private readonly http: HttpClient) {}

  list(q?: string): Observable<Cliente[]> {
    let params = new HttpParams();

    if (q && q.trim().length > 0) params = params.set('q', q.trim());

    return this.http.get<Cliente[]>(this.baseUrl, { params });
  }

  getById(id: number): Observable<Cliente> {

    return this.http.get<Cliente>(`${this.baseUrl}/${id}`);
  }

  create(payload: ClienteCreateDto): Observable<Cliente> {


    return this.http.post<Cliente>(this.baseUrl, payload);
  }

  update(id: number, payload: ClienteUpdateDto): Observable<Cliente> {

    return this.http.patch<Cliente>(`${this.baseUrl}/${id}`, payload);
  }

  patch(id: number, payload: ClienteUpdateDto): Observable<Cliente> {

    return this.http.patch<Cliente>(`${this.baseUrl}/${id}`, payload);
  }

  delete(id: number): Observable<void> {

    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
