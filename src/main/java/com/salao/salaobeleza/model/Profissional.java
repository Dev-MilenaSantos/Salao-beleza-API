package com.salao.salaobeleza.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profissionais")
@Getter @Setter @NoArgsConstructor
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    private String telefone;
    private String especialidade;

    public Profissional(String nome, String telefone, String especialidade) {
        this.nome = nome;
        this.telefone = telefone;
        this.especialidade = especialidade;
    }
}
