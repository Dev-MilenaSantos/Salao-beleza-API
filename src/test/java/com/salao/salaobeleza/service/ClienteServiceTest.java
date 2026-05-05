package com.salao.salaobeleza.service;

import com.salao.salaobeleza.dto.ClienteRequest;
import com.salao.salaobeleza.dto.ClienteResponse;
import com.salao.salaobeleza.exception.RecursoNaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @Test
    void deveSalvarERetornarCliente() {
        ClienteResponse response = clienteService.criar(new ClienteRequest("Ana Silva", "21999999999", "ana@email.com"));
        assertThat(response.id()).isNotNull();
        assertThat(response.nome()).isEqualTo("Ana Silva");
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        assertThatThrownBy(() -> clienteService.buscarPorId(999L))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("Cliente não encontrado");
    }

    @Test
    void deveListarClientes() {
        clienteService.criar(new ClienteRequest("João", "21911111111", "joao@email.com"));
        clienteService.criar(new ClienteRequest("Maria", "21922222222", "maria@email.com"));
        List<ClienteResponse> lista = clienteService.listar();
        assertThat(lista).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void deveAtualizarCliente() {
        ClienteResponse criado = clienteService.criar(new ClienteRequest("Antigo", "21900000000", "antigo@email.com"));
        ClienteResponse atualizado = clienteService.atualizar(criado.id(), new ClienteRequest("Novo", "21911111111", "novo@email.com"));
        assertThat(atualizado.nome()).isEqualTo("Novo");
    }

    @Test
    void deveDeletarCliente() {
        ClienteResponse criado = clienteService.criar(new ClienteRequest("Para Deletar", "21933333333", "del@email.com"));
        clienteService.deletar(criado.id());
        assertThatThrownBy(() -> clienteService.buscarPorId(criado.id()))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}