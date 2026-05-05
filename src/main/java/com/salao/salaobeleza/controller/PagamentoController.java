package com.salao.salaobeleza.controller;

import com.salao.salaobeleza.dto.PagamentoRequest;
import com.salao.salaobeleza.dto.PagamentoResponse;
import com.salao.salaobeleza.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PagamentoResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public PagamentoResponse buscarPorId(@PathVariable Long id) { return service.buscarPorId(id); }

    @GetMapping("/agendamento/{id}")
    public PagamentoResponse buscarPorAgendamento(@PathVariable Long id) { return service.buscarPorAgendamento(id); }

    @PostMapping
    public ResponseEntity<PagamentoResponse> registrar(@RequestBody @Valid PagamentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }
}