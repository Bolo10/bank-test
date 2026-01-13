package com.bank.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Persona {
    //palabra extends indica herencia
    @Column(unique = true)
    private String clientId;

    @NotBlank
    private String password;

    private Boolean estado;
}
