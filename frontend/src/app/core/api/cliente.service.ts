import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente, ClienteCreate, ClienteUpdate } from '../models/cliente-model';

@Injectable({ providedIn: 'root' })
export class ClienteService {
  private readonly baseUrl = '/api/clientes';

  constructor(private readonly http: HttpClient) {}

  list(q?: string): Observable<Cliente[]> {
    console.log(q);
    let params = new HttpParams();


    if (q && q.trim().length > 0) params = params.set('q', q.trim());


    return this.http.get<Cliente[]>(this.baseUrl, { params });
  }

  getById(id: number): Observable<Cliente> {
    console.log(id);
    return this.http.get<Cliente>(`${this.baseUrl}/${id}`);
  }

  create(payload: ClienteCreate): Observable<Cliente> {
    console.log(payload);

    return this.http.post<Cliente>(this.baseUrl, payload);
  }

  update(id: number, payload: ClienteUpdate): Observable<Cliente> {
    console.log(payload);
    return this.http.put<Cliente>(`${this.baseUrl}/${id}`, payload);
  }

  patch(id: number, payload: ClienteUpdate): Observable<Cliente> {
    console.log(payload);
    return this.http.patch<Cliente>(`${this.baseUrl}/${id}`, payload);
  }




  delete(id: number): Observable<void> {
    console.log(id);
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
