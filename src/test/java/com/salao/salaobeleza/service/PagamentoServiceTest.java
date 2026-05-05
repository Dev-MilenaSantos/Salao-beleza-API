package com.salao.salaobeleza.service;

import com.salao.salaobeleza.dto.*;
import com.salao.salaobeleza.exception.ConflitoException;
import com.salao.salaobeleza.exception.RecursoNaoEncontradoException;
import com.salao.salaobeleza.model.enums.FormaPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PagamentoServiceTest {

    @Autowired private PagamentoService pagamentoService;
    @Autowired private AgendamentoService agendamentoService;
    @Autowired private ClienteService clienteService;
    @Autowired private ProfissionalService profissionalService;
    @Autowired private ServicoService servicoService;

    private Long agendamentoId;

    @BeforeEach
    void setUp() {
        Long clienteId = clienteService.criar(new ClienteRequest("Ana", "21999999999", "ana@email.com")).id();
        Long profissionalId = profissionalService.criar(new ProfissionalRequest("Lucia", "21988888888", "Cabelereira")).id();
        Long servicoId = servicoService.criar(new ServicoRequest("Corte", null, 60, new BigDecimal("80.00"))).id();
        agendamentoId = agendamentoService.criar(
                new AgendamentoRequest(clienteId, profissionalId, servicoId, LocalDateTime.of(2026, 6, 10, 10, 0))).id();
    }

    @Test
    void deveRegistrarPagamento() {
        PagamentoResponse response = pagamentoService.registrar(new PagamentoRequest(agendamentoId, new BigDecimal("80.00"), FormaPagamento.PIX));
        assertThat(response.id()).isNotNull();
        assertThat(response.agendamentoId()).isEqualTo(agendamentoId);
        assertThat(response.formaPagamento()).isEqualTo(FormaPagamento.PIX);
        assertThat(response.dataPagamento()).isNotNull();
    }

    @Test
    void deveLancarConflitoQuandoAgendamentoJaTemPagamento() {
        pagamentoService.registrar(new PagamentoRequest(agendamentoId, new BigDecimal("80.00"), FormaPagamento.CARTAO));
        assertThatThrownBy(() -> pagamentoService.registrar(new PagamentoRequest(agendamentoId, new BigDecimal("80.00"), FormaPagamento.PIX)))
                .isInstanceOf(ConflitoException.class)
                .hasMessageContaining("já possui pagamento");
    }

    @Test
    void deveBuscarPagamentoPorAgendamento() {
        pagamentoService.registrar(new PagamentoRequest(agendamentoId, new BigDecimal("80.00"), FormaPagamento.DINHEIRO));
        PagamentoResponse encontrado = pagamentoService.buscarPorAgendamento(agendamentoId);
        assertThat(encontrado.agendamentoId()).isEqualTo(agendamentoId);
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoNaoEncontrado() {
        assertThatThrownBy(() -> pagamentoService.buscarPorId(999L))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("Pagamento não encontrado");
    }
}