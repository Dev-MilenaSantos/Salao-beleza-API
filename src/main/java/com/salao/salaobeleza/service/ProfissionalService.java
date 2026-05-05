package com.salao.salaobeleza.service;

import com.salao.salaobeleza.dto.ProfissionalRequest;
import com.salao.salaobeleza.dto.ProfissionalResponse;
import com.salao.salaobeleza.exception.RecursoNaoEncontradoException;
import com.salao.salaobeleza.model.Profissional;
import com.salao.salaobeleza.repository.ProfissionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfissionalService {

    private final ProfissionalRepository repository;

    public ProfissionalService(ProfissionalRepository repository) {
        this.repository = repository;
    }

    public ProfissionalResponse criar(ProfissionalRequest request) {
        return toResponse(repository.save(new Profissional(request.nome(), request.telefone(), request.especialidade())));
    }

    public ProfissionalResponse buscarPorId(Long id) {
        return toResponse(encontrarOuLancar(id));
    }

    public List<ProfissionalResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ProfissionalResponse atualizar(Long id, ProfissionalRequest request) {
        Profissional p = encontrarOuLancar(id);
        p.setNome(request.nome());
        p.setTelefone(request.telefone());
        p.setEspecialidade(request.especialidade());
        return toResponse(repository.save(p));
    }

    public void deletar(Long id) {
        encontrarOuLancar(id);
        repository.deleteById(id);
    }

    Profissional encontrarOuLancar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado com id: " + id));
    }

    private ProfissionalResponse toResponse(Profissional p) {
        return new ProfissionalResponse(p.getId(), p.getNome(), p.getTelefone(), p.getEspecialidade());
    }
}