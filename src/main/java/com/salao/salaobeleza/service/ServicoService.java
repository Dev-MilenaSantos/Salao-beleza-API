package com.salao.salaobeleza.service;

import com.salao.salaobeleza.dto.ServicoRequest;
import com.salao.salaobeleza.dto.ServicoResponse;
import com.salao.salaobeleza.exception.RecursoNaoEncontradoException;
import com.salao.salaobeleza.model.Servico;
import com.salao.salaobeleza.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository repository;

    public ServicoService(ServicoRepository repository) {
        this.repository = repository;
    }

    public ServicoResponse criar(ServicoRequest request) {
        return toResponse(repository.save(new Servico(request.nome(), request.descricao(), request.duracao(), request.preco())));
    }

    public ServicoResponse buscarPorId(Long id) {
        return toResponse(encontrarOuLancar(id));
    }

    public List<ServicoResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ServicoResponse atualizar(Long id, ServicoRequest request) {
        Servico s = encontrarOuLancar(id);
        s.setNome(request.nome());
        s.setDescricao(request.descricao());
        s.setDuracao(request.duracao());
        s.setPreco(request.preco());
        return toResponse(repository.save(s));
    }

    public void deletar(Long id) {
        encontrarOuLancar(id);
        repository.deleteById(id);
    }

    Servico encontrarOuLancar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado com id: " + id));
    }

    private ServicoResponse toResponse(Servico s) {
        return new ServicoResponse(s.getId(), s.getNome(), s.getDescricao(), s.getDuracao(), s.getPreco());
    }
}