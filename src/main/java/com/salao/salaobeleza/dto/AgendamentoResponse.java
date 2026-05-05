package com.salao.salaobeleza.dto;

import com.salao.salaobeleza.model.enums.StatusAgendamento;
import java.time.LocalDateTime;

public record AgendamentoResponse(
        Long id,
        ClienteResponse cliente,
        ProfissionalResponse profissional,
        ServicoResponse servico,
        LocalDateTime dataHora,
        StatusAgendamento status
) {}