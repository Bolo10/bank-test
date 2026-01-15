import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';

import { ActivatedRoute, RouterModule } from '@angular/router';
import { ClienteService } from '../app/core/api/services/cliente.service';
import { ClienteFormPageComponent } from '../app/features/clientes/form/cliente-form-page.component';

//Valide que el componente CLIENTE-FORM-PAGE se cree sin depender de servicios o rutas reales

describe('ClienteFormPageComponent', () => {
  const activatedRouteMock = {
    snapshot: { paramMap: { get: jest.fn().mockReturnValue(null) } },
    params: of({}),
  };

  const clienteServiceMock = {
    create: jest.fn().mockReturnValue(of({ id: 1 })),
    update: jest.fn().mockReturnValue(of({ id: 1 })),
  } as any;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterModule, ClienteFormPageComponent],
      providers: [
        { provide: ActivatedRoute, useValue: activatedRouteMock },
        { provide: ClienteService, useValue: clienteServiceMock },
      ],
    }).compileComponents();

    jest.clearAllMocks();
  });

  it('debe crearse', () => {
    const fixture = TestBed.createComponent(ClienteFormPageComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
