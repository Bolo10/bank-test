export interface Cliente {
  id: number;
  clientId: string;
  nombre: string;
  genero: string;
  edad: number;
  identificacion: string;
  direccion: string;
  telefono: string;
  estado: boolean;
}

export interface ClienteCreateDto {
  nombre: string;
  genero: string;
  edad: number;
  identificacion: string;
  direccion: string;
  telefono: string;

  estado: boolean;
}

export interface ClienteUpdateDto {
  nombre?: string;
  genero?: string;
  edad?: number;
  identificacion?: string;
  direccion?: string;
  telefono?: string;

  estado?: boolean;
}
