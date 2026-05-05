package com.salao.salaobeleza.service;

import com.salao.salaobeleza.dto.*;
import com.salao.salaobeleza.exception.ConflitoException;
import com.salao.salaobeleza.exception.RecursoNaoEncontradoException;
import com.salao.salaobeleza.model.enums.StatusAgendamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AgendamentoServiceTest {

    @Autowired private AgendamentoService agendamentoService;
    @Autowired private ClienteService clienteService;
    @Autowired private ProfissionalService profissionalService;
    @Autowired private ServicoService servicoService;

    private Long clienteId;
    private Long profissionalId;
    private Long servicoId;
    private LocalDateTime horario;

    @BeforeEach
    void setUp() {
        clienteId = clienteService.criar(new ClienteRequest("Ana", "21999999999", "ana@email.com")).id();
        profissionalId = profissionalService.criar(new ProfissionalRequest("Lucia", "21988888888", "Cabelereira")).id();
        servicoId = servicoService.criar(new ServicoRequest("Corte", null, 60, new BigDecimal("80.00"))).id();
        horario = LocalDateTime.of(2026, 5, 10, 10, 0);
    }

    @Test
    void deveCriarAgendamento() {
        AgendamentoResponse response = agendamentoService.criar(new AgendamentoRequest(clienteId, profissionalId, servicoId, horario));
        assertThat(response.id()).isNotNull();
        assertThat(response.status()).isEqualTo(StatusAgendamento.CONFIRMADO);
    }

    @Test
    void deveLancarConflitoQuandoProfissionalJaTemAgendamentoNoMesmoHorario() {
        agendamentoService.criar(new AgendamentoRequest(clienteId, profissionalId, servicoId, horario));
        Long outroClienteId = clienteService.criar(new ClienteRequest("Pedro", "21977777777", "pedro@email.com")).id();

        assertThatThrownBy(() -> agendamentoService.criar(new AgendamentoRequest(outroClienteId, profissionalId, servicoId, horario)))
                .isInstanceOf(ConflitoException.class)
                .hasMessageContaining("já possui agendamento");
    }

    @Test
    void devePermitirMesmoProfissionalEmHorariosDistintos() {
        agendamentoService.criar(new AgendamentoRequest(clienteId, profissionalId, servicoId, horario));
        AgendamentoResponse segundo = agendamentoService.criar(
                new AgendamentoRequest(clienteId, profissionalId, servicoId, horario.plusHours(1)));
        assertThat(segundo.id()).isNotNull();
    }

    @Test
    void deveAtualizarStatusDoAgendamento() {
        AgendamentoResponse criado = agendamentoService.criar(new AgendamentoRequest(clienteId, profissionalId, servicoId, horario));
        AgendamentoResponse atualizado = agendamentoService.atualizarStatus(criado.id(), new AtualizaStatusRequest(StatusAgendamento.CONCLUIDO));
        assertThat(atualizado.status()).isEqualTo(StatusAgendamento.CONCLUIDO);
    }

    @Test
    void deveBuscarAgendamentosPorProfissional() {
        agendamentoService.criar(new AgendamentoRequest(clienteId, profissionalId, servicoId, horario));
        List<AgendamentoResponse> lista = agendamentoService.buscarPorProfissional(profissionalId);
        assertThat(lista).hasSize(1);
    }

    @Test
    void deveBuscarAgendamentosPorCliente() {
        agendamentoService.criar(new AgendamentoRequest(clienteId, profissionalId, servicoId, horario));
        List<AgendamentoResponse> lista = agendamentoService.buscarPorCliente(clienteId);
        assertThat(lista).hasSize(1);
    }

    @Test
    void deveLancarExcecaoQuandoAgendamentoNaoEncontrado() {
        assertThatThrownBy(() -> agendamentoService.buscarPorId(999L))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("Agendamento não encontrado");
    }
}