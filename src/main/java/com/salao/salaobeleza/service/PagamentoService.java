package com.salao.salaobeleza.service;

import com.salao.salaobeleza.dto.PagamentoRequest;
import com.salao.salaobeleza.dto.PagamentoResponse;
import com.salao.salaobeleza.exception.ConflitoException;
import com.salao.salaobeleza.exception.RecursoNaoEncontradoException;
import com.salao.salaobeleza.model.Agendamento;
import com.salao.salaobeleza.model.Pagamento;
import com.salao.salaobeleza.repository.AgendamentoRepository;
import com.salao.salaobeleza.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final AgendamentoRepository agendamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, AgendamentoRepository agendamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.agendamentoRepository = agendamentoRepository;
    }

    public PagamentoResponse registrar(PagamentoRequest request) {
        if (pagamentoRepository.existsByAgendamentoId(request.agendamentoId())) {
            throw new ConflitoException("Agendamento já possui pagamento registrado.");
        }
        Agendamento agendamento = agendamentoRepository.findById(request.agendamentoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado com id: " + request.agendamentoId()));
        return toResponse(pagamentoRepository.save(new Pagamento(agendamento, request.valor(), request.formaPagamento())));
    }

    public PagamentoResponse buscarPorId(Long id) {
        return toResponse(pagamentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento não encontrado com id: " + id)));
    }

    public List<PagamentoResponse> listar() {
        return pagamentoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public PagamentoResponse buscarPorAgendamento(Long agendamentoId) {
        return toResponse(pagamentoRepository.findByAgendamentoId(agendamentoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento não encontrado para agendamento id: " + agendamentoId)));
    }

    private PagamentoResponse toResponse(Pagamento p) {
        return new PagamentoResponse(p.getId(), p.getAgendamento().getId(), p.getValor(), p.getFormaPagamento(), p.getDataPagamento());
    }
}