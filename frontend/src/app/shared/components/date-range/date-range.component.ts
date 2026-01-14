import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
export type DateRangeValue = {
  desde: string;
  hasta: string;
  valid: boolean;
  message: string;
};

@Component({
  selector: 'app-date-range',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './date-range.component.html',
  styleUrl: './date-range.component.css',
})
export class DateRangeComponent {
  labelDesde = 'Fecha inicio';
  labelHasta = 'Fecha fin';

  @Output() valueChange = new EventEmitter<DateRangeValue>();

  message = '';
  desde: string = '';
  hasta: string = '';

  emit(): void {
    this.message = '';

    let valid = true;

    if (this.desde && this.hasta && this.desde > this.hasta) {
      valid = false;
      this.message = 'La fecha inicio no puede ser mayor que la fecha fin.';
    }

    this.valueChange.emit({
      desde: this.desde,
      hasta: this.hasta,
      valid,
      message: this.message,
    });
  }
}
