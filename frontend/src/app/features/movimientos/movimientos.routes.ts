import { Routes } from '@angular/router';
import { MovimientosPageComponent } from './movimientos-page.component';
import { MovimientoFormPageComponent } from './form/movimientos-form-page.component';

export const MOVIMIENTOS_ROUTES: Routes = [
  { path: '', component: MovimientosPageComponent }, // /movimientos
  { path: 'nuevo', component: MovimientoFormPageComponent }, // /movimientos/nuevo
];
