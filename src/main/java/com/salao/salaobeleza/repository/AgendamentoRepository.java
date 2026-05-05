package com.salao.salaobeleza.repository;

import com.salao.salaobeleza.model.Agendamento;
import com.salao.salaobeleza.model.enums.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByProfissionalId(Long profissionalId);

    List<Agendamento> findByClienteId(Long clienteId);

    boolean existsByProfissionalIdAndDataHoraAndStatus(
            Long profissionalId, LocalDateTime dataHora, StatusAgendamento status);
}