package com.salao.salaobeleza.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AgendamentoRequest(
        @NotNull(message = "Cliente é obrigatório") Long clienteId,
        @NotNull(message = "Profissional é obrigatório") Long profissionalId,
        @NotNull(message = "Serviço é obrigatório") Long servicoId,
        @NotNull(message = "Data/hora é obrigatória") LocalDateTime dataHora
) {}