import { Routes } from '@angular/router';
import { CuentasPageComponent } from './cuentas-page.component';
import { CuentaFormPageComponent } from '../cuentas/form/cuentas-form-page.component';
export const CUENTAS_ROUTES: Routes = [
  { path: '', component: CuentasPageComponent }, // /cuentas
  { path: 'nuevo', component: CuentaFormPageComponent }, // /cuentas/nuevo
  { path: ':id/editar', component: CuentaFormPageComponent }, // /cuentas/:id/editar
];
