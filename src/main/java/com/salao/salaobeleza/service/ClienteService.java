package com.salao.salaobeleza.service;

import com.salao.salaobeleza.dto.ClienteRequest;
import com.salao.salaobeleza.dto.ClienteResponse;
import com.salao.salaobeleza.exception.RecursoNaoEncontradoException;
import com.salao.salaobeleza.model.Cliente;
import com.salao.salaobeleza.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public ClienteResponse criar(ClienteRequest request) {
        return toResponse(repository.save(new Cliente(request.nome(), request.telefone(), request.email())));
    }

    public ClienteResponse buscarPorId(Long id) {
        return toResponse(encontrarOuLancar(id));
    }

    public List<ClienteResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ClienteResponse atualizar(Long id, ClienteRequest request) {
        Cliente cliente = encontrarOuLancar(id);
        cliente.setNome(request.nome());
        cliente.setTelefone(request.telefone());
        cliente.setEmail(request.email());
        return toResponse(repository.save(cliente));
    }

    public void deletar(Long id) {
        encontrarOuLancar(id);
        repository.deleteById(id);
    }

    Cliente encontrarOuLancar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com id: " + id));
    }

    private ClienteResponse toResponse(Cliente c) {
        return new ClienteResponse(c.getId(), c.getNome(), c.getTelefone(), c.getEmail());
    }
}