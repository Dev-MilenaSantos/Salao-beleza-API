package com.salao.salaobeleza.service;

import com.salao.salaobeleza.dto.ServicoRequest;
import com.salao.salaobeleza.dto.ServicoResponse;
import com.salao.salaobeleza.exception.RecursoNaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ServicoServiceTest {

    @Autowired
    private ServicoService servicoService;

    @Test
    void deveSalvarERetornarServico() {
        ServicoResponse response = servicoService.criar(new ServicoRequest("Corte feminino", "Com lavagem", 60, new BigDecimal("80.00")));
        assertThat(response.id()).isNotNull();
        assertThat(response.preco()).isEqualByComparingTo(new BigDecimal("80.00"));
    }

    @Test
    void deveLancarExcecaoQuandoServicoNaoEncontrado() {
        assertThatThrownBy(() -> servicoService.buscarPorId(999L))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("Serviço não encontrado");
    }

    @Test
    void deveListarServicos() {
        servicoService.criar(new ServicoRequest("Manicure", null, 45, new BigDecimal("40.00")));
        servicoService.criar(new ServicoRequest("Pedicure", null, 60, new BigDecimal("50.00")));
        List<ServicoResponse> lista = servicoService.listar();
        assertThat(lista).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void deveAtualizarServico() {
        ServicoResponse criado = servicoService.criar(new ServicoRequest("Antigo", null, 30, new BigDecimal("30.00")));
        ServicoResponse atualizado = servicoService.atualizar(criado.id(), new ServicoRequest("Novo", "Desc", 45, new BigDecimal("60.00")));
        assertThat(atualizado.nome()).isEqualTo("Novo");
        assertThat(atualizado.preco()).isEqualByComparingTo(new BigDecimal("60.00"));
    }

    @Test
    void deveDeletarServico() {
        ServicoResponse criado = servicoService.criar(new ServicoRequest("Deletar", null, 30, new BigDecimal("20.00")));
        servicoService.deletar(criado.id());
        assertThatThrownBy(() -> servicoService.buscarPorId(criado.id()))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}