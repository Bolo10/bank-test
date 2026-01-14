import { Routes } from '@angular/router';
import { ClientesPageComponent } from './cliente-page.component';
import { ClienteFormPageComponent } from './form/cliente-form-page.component';

export const CLIENTES_ROUTES: Routes = [
  { path: '', component: ClientesPageComponent }, // /clientes
  { path: 'nuevo', component: ClienteFormPageComponent }, // /clientes/nuevo
  { path: ':id/editar', component: ClienteFormPageComponent }, // /clientes/:id/editar
];
