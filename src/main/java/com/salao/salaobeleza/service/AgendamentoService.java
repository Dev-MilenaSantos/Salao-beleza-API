package com.salao.salaobeleza.service;

import com.salao.salaobeleza.dto.*;
import com.salao.salaobeleza.exception.ConflitoException;
import com.salao.salaobeleza.exception.RecursoNaoEncontradoException;
import com.salao.salaobeleza.model.Agendamento;
import com.salao.salaobeleza.model.enums.StatusAgendamento;
import com.salao.salaobeleza.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final ClienteService clienteService;
    private final ProfissionalService profissionalService;
    private final ServicoService servicoService;

    public AgendamentoService(AgendamentoRepository repository,
                              ClienteService clienteService,
                              ProfissionalService profissionalService,
                              ServicoService servicoService) {
        this.repository = repository;
        this.clienteService = clienteService;
        this.profissionalService = profissionalService;
        this.servicoService = servicoService;
    }

    public AgendamentoResponse criar(AgendamentoRequest request) {
        if (repository.existsByProfissionalIdAndDataHoraAndStatus(
                request.profissionalId(), request.dataHora(), StatusAgendamento.CONFIRMADO)) {
            throw new ConflitoException("Profissional já possui agendamento neste horário.");
        }
        var agendamento = new Agendamento(
                clienteService.encontrarOuLancar(request.clienteId()),
                profissionalService.encontrarOuLancar(request.profissionalId()),
                servicoService.encontrarOuLancar(request.servicoId()),
                request.dataHora()
        );
        return toResponse(repository.save(agendamento));
    }

    public AgendamentoResponse buscarPorId(Long id) {
        return toResponse(encontrarOuLancar(id));
    }

    public List<AgendamentoResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public List<AgendamentoResponse> buscarPorProfissional(Long profissionalId) {
        return repository.findByProfissionalId(profissionalId).stream().map(this::toResponse).toList();
    }

    public List<AgendamentoResponse> buscarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId).stream().map(this::toResponse).toList();
    }

    public AgendamentoResponse atualizarStatus(Long id, AtualizaStatusRequest request) {
        Agendamento agendamento = encontrarOuLancar(id);
        agendamento.setStatus(request.status());
        return toResponse(repository.save(agendamento));
    }

    public void deletar(Long id) {
        Agendamento agendamento = encontrarOuLancar(id);
        agendamento.setStatus(StatusAgendamento.CANCELADO);
        repository.save(agendamento);
    }

    Agendamento encontrarOuLancar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado com id: " + id));
    }

    private AgendamentoResponse toResponse(Agendamento a) {
        var c = a.getCliente();
        var p = a.getProfissional();
        var s = a.getServico();
        return new AgendamentoResponse(
                a.getId(),
                new ClienteResponse(c.getId(), c.getNome(), c.getTelefone(), c.getEmail()),
                new ProfissionalResponse(p.getId(), p.getNome(), p.getTelefone(), p.getEspecialidade()),
                new ServicoResponse(s.getId(), s.getNome(), s.getDescricao(), s.getDuracao(), s.getPreco()),
                a.getDataHora(),
                a.getStatus()
        );
    }
}