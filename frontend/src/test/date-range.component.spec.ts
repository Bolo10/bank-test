import { TestBed } from '@angular/core/testing';
import { DateRangeComponent } from '../app/shared/components/date-range/date-range.component';

// Valide que el componente DATE-RANGE se cree y emita si esta correcto el rango de fechas
describe('DateRangeComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DateRangeComponent],
    })
      .overrideComponent(DateRangeComponent, {
        set: {
          template: '<div></div>', // mock del template
        },
      })
      .compileComponents();
  });

  it('debe crear el componente', () => {
    const fixture = TestBed.createComponent(DateRangeComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });

  it('debe emitir valid=true cuando el rango es correcto', () => {
    const fixture = TestBed.createComponent(DateRangeComponent);
    const component = fixture.componentInstance;

    component.desde = '2026-01-13';
    component.hasta = '2026-01-15';

    let emitted: any = null;
    component.valueChange.subscribe((v) => (emitted = v));

    component.emit();

    expect(emitted).not.toBeNull();
    expect(emitted.valid).toBe(true);
    expect(emitted.message).toBe('');
    expect(component.message).toBe('');
  });

  it('debe emitir valid=false y mensaje cuando desde > hasta', () => {
    const fixture = TestBed.createComponent(DateRangeComponent);
    const component = fixture.componentInstance;

    component.desde = '2026-01-15';
    component.hasta = '2026-01-13';

    let emitted: any = null;
    component.valueChange.subscribe((v) => (emitted = v));

    component.emit();

    expect(emitted).not.toBeNull();
    expect(emitted.valid).toBe(false);
    expect(emitted.message).toContain('fecha inicio');
    expect(component.message).toContain('fecha inicio');
  });
});
