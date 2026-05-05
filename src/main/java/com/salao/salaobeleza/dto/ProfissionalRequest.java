package com.salao.salaobeleza.dto;

import jakarta.validation.constraints.NotBlank;

public record ProfissionalRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        String telefone,
        String especialidade
) {}