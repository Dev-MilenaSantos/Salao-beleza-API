package com.salao.salaobeleza.repository;

import com.salao.salaobeleza.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}