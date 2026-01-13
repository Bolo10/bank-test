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

    @Min(0)
    protected Integer edad;

    @NotBlank
    @Column(unique = true, nullable = false)
    protected String identificacion;

    @NotBlank
    protected String direccion;

    @NotBlank
    protected String telefono;
}
