package com.salao.salaobeleza.controller;

import com.salao.salaobeleza.dto.AgendamentoRequest;
import com.salao.salaobeleza.dto.AgendamentoResponse;
import com.salao.salaobeleza.dto.AtualizaStatusRequest;
import com.salao.salaobeleza.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<AgendamentoResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public AgendamentoResponse buscarPorId(@PathVariable Long id) { return service.buscarPorId(id); }

    @GetMapping("/profissional/{id}")
    public List<AgendamentoResponse> buscarPorProfissional(@PathVariable Long id) {
        return service.buscarPorProfissional(id);
    }

    @GetMapping("/cliente/{id}")
    public List<AgendamentoResponse> buscarPorCliente(@PathVariable Long id) {
        return service.buscarPorCliente(id);
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponse> criar(@RequestBody @Valid AgendamentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PutMapping("/{id}/status")
    public AgendamentoResponse atualizarStatus(@PathVariable Long id, @RequestBody @Valid AtualizaStatusRequest request) {
        return service.atualizarStatus(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}