package com.salao.salaobeleza.dto;

import java.math.BigDecimal;

public record ServicoResponse(Long id, String nome, String descricao, Integer duracao, BigDecimal preco) {}