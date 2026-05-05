package com.salao.salaobeleza.repository;

import com.salao.salaobeleza.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    boolean existsByAgendamentoId(Long agendamentoId);

    Optional<Pagamento> findByAgendamentoId(Long agendamentoId);
}