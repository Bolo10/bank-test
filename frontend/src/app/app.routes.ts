import { Routes } from '@angular/router';
import { ShellComponent } from './layout/shell/shell.component';

export const routes: Routes = [
  {
    path: '',
    component: ShellComponent,
    children: [{ path: '', pathMatch: 'full', redirectTo: 'clientes' }],
  },
  { path: '**', redirectTo: '' },
];
