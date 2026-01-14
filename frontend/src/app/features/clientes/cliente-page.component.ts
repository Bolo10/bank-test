import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClienteService } from '../../core/api/cliente.service';
import { Cliente } from '../../core/models/cliente.model';
import { RouterLink } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cliente-page.component.html',
  styleUrl: './cliente-page.component.css',
})
export class ClientesPageComponent implements OnInit {
  clientes: Cliente[] = [];
  q = '';
  loading = false;
  error = '';

  constructor(private readonly clienteService: ClienteService) {}

  ngOnInit(): void {
    this.reload();
  }

  reload(): void {
    this.fetch();
  }

  onSearch(): void {
    this.fetch();
  }

  private fetch(): void {
    this.loading = true;
    this.error = '';
    this.clienteService.list(this.q).subscribe({
      next: (data) => {
        this.clientes = data ?? [];
        this.loading = false;
      },
      error: (err) => {
        this.error = err?.error?.message ?? 'Error cargando clientes';
        this.loading = false;
      },
    });
  }
}
