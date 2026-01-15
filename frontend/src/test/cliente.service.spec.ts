import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import {
  provideHttpClientTesting,
  HttpTestingController,
} from '@angular/common/http/testing';
import { ClienteService } from '../app/core/api/services/cliente.service';

// validé que el método list funcione correctamente

describe('ClienteService', () => {
  let service: ClienteService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ClienteService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(ClienteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('list() debe hacer GET a /clientes', () => {
    service.list('').subscribe((res) => {
      expect(Array.isArray(res)).toBe(true);
    });

    const req = httpMock.expectOne(
      (r) => r.method === 'GET' && r.url.includes('/clientes')
    );

    req.flush([]);
  });
});
