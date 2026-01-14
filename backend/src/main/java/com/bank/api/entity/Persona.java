package com.bank.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank
    @Column(nullable = false)
    protected String nombre;

    @NotBlank
    protected String genero;

    @Min(value = 0, message = "La edad no puede ser menor a 0")
    @Max(value = 100, message = "La edad no puede ser mayor a 100")
    protected Integer edad;

    @NotBlank
    @Column(unique = true, nullable = false)
    protected String identificacion;

    @NotBlank
    protected String direccion;

    @NotBlank
    protected String telefono;
}
