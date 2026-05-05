package com.salao.salaobeleza.dto;

import com.salao.salaobeleza.model.enums.FormaPagamento;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponse(
        Long id,
        Long agendamentoId,
        BigDecimal valor,
        FormaPagamento formaPagamento,
        LocalDateTime dataPagamento
) {}