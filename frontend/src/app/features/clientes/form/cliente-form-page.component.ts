import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ClienteService } from '../../../core/api/services/cliente.service';
import {
  Cliente,
  ClienteCreateDto,
  ClienteUpdateDto,
} from '../../../core/models/cliente.model';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cliente-form-page.component.html',
  styleUrl: './cliente-form-page.component.css',
})
export class ClienteFormPageComponent implements OnInit {
  isEdit = false;
  id: number | null = null;

  saving = false;
  error = '';
  ok = '';

  model: {
    nombre: string;
    genero: string;
    edad: number | null;
    identificacion: string;
    direccion: string;
    telefono: string;
    //password: string;
    estado: boolean;
  } = {
    nombre: '',
    genero: '',
    edad: null,
    identificacion: '',
    direccion: '',
    telefono: '',
    //password: '',
    estado: true,
  };

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly clienteService: ClienteService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEdit = true;
      this.id = Number(idParam);
      this.loadCliente(this.id);
    }
  }

  private loadCliente(id: number): void {
    this.clienteService.getById(id).subscribe({
      next: (c: Cliente) => {
        this.model.nombre = c.nombre ?? '';
        this.model.genero = c.genero ?? '';
        this.model.edad = c.edad ?? null;
        this.model.identificacion = c.identificacion ?? '';
        this.model.direccion = c.direccion ?? '';
        this.model.telefono = c.telefono ?? '';
        this.model.estado = !!c.estado;
        //this.model.password = ''; // no deberia venir del backend
      },
      error: (err) => {
        this.error = err?.error?.message ?? 'No se pudo cargar el cliente';
      },
    });
  }

  onSubmit(f: NgForm): void {
    this.error = '';
    this.ok = '';

    if (f.invalid) {
      this.error = 'Revisa los campos requeridos.';
      return;
    }

    this.saving = true;

    if (!this.isEdit) {
      const payload: ClienteCreateDto = {
        nombre: this.model.nombre.trim(),
        genero: this.model.genero,
        edad: Number(this.model.edad),
        identificacion: this.model.identificacion.trim(),
        direccion: this.model.direccion.trim(),
        telefono: this.model.telefono.trim(),
        //password: this.model.password,
        estado: this.model.estado,
      };

      this.clienteService.create(payload).subscribe({
        next: () => {
          this.saving = false;
          this.router.navigate(['/clientes']);
        },
        error: (err) => {
          this.saving = false;
          this.error = err?.error?.message ?? 'Error al crear cliente';
        },
      });

      return;
    }

    // edit
    const update: ClienteUpdateDto = {
      nombre: this.model.nombre.trim(),
      genero: this.model.genero,
      edad: Number(this.model.edad),
      identificacion: this.model.identificacion.trim(),
      direccion: this.model.direccion.trim(),
      telefono: this.model.telefono.trim(),
      estado: this.model.estado,
      //...(this.model.password?.trim()
      //  ? { password: this.model.password }
      //  : {}),
    };

    this.clienteService.update(this.id!, update).subscribe({
      next: () => {
        this.saving = false;
        this.router.navigate(['/clientes']);
      },
      error: (err) => {
        this.saving = false;
        this.error = err?.error?.message ?? 'Error al actualizar cliente';
      },
    });
  }
}
