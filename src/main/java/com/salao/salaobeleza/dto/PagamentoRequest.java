package com.salao.salaobeleza.dto;

import com.salao.salaobeleza.model.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PagamentoRequest(
        @NotNull(message = "Agendamento é obrigatório") Long agendamentoId,
        @NotNull @Positive BigDecimal valor,
        @NotNull(message = "Forma de pagamento é obrigatória") FormaPagamento formaPagamento
) {}