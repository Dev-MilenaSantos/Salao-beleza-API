package com.salao.salaobeleza.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ServicoRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        String descricao,
        @NotNull @Positive Integer duracao,
        @NotNull @Positive BigDecimal preco
) {}