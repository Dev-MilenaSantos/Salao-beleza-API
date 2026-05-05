package com.salao.salaobeleza.dto;

import com.salao.salaobeleza.model.enums.StatusAgendamento;
import jakarta.validation.constraints.NotNull;

public record AtualizaStatusRequest(@NotNull StatusAgendamento status) {}