package com.salao.salaobeleza.service;

import com.salao.salaobeleza.dto.ProfissionalRequest;
import com.salao.salaobeleza.dto.ProfissionalResponse;
import com.salao.salaobeleza.exception.RecursoNaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ProfissionalServiceTest {

    @Autowired
    private ProfissionalService profissionalService;

    @Test
    void deveSalvarERetornarProfissional() {
        ProfissionalResponse response = profissionalService.criar(new ProfissionalRequest("Lucia Souza", "21977777777", "Cabelereira"));
        assertThat(response.id()).isNotNull();
        assertThat(response.especialidade()).isEqualTo("Cabelereira");
    }

    @Test
    void deveLancarExcecaoQuandoProfissionalNaoEncontrado() {
        assertThatThrownBy(() -> profissionalService.buscarPorId(999L))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("Profissional não encontrado");
    }

    @Test
    void deveListarProfissionais() {
        profissionalService.criar(new ProfissionalRequest("Fernanda", "21955555555", "Manicure"));
        profissionalService.criar(new ProfissionalRequest("Roberto", "21944444444", "Barbeiro"));
        List<ProfissionalResponse> lista = profissionalService.listar();
        assertThat(lista).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void deveAtualizarProfissional() {
        ProfissionalResponse criado = profissionalService.criar(new ProfissionalRequest("Antigo", "21900000000", "Colorista"));
        ProfissionalResponse atualizado = profissionalService.atualizar(criado.id(), new ProfissionalRequest("Novo", "21911111111", "Manicure"));
        assertThat(atualizado.nome()).isEqualTo("Novo");
        assertThat(atualizado.especialidade()).isEqualTo("Manicure");
    }

    @Test
    void deveDeletarProfissional() {
        ProfissionalResponse criado = profissionalService.criar(new ProfissionalRequest("Deletar", "21933333333", "Pedicure"));
        profissionalService.deletar(criado.id());
        assertThatThrownBy(() -> profissionalService.buscarPorId(criado.id()))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}