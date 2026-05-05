package com.salao.salaobeleza.model;

import com.salao.salaobeleza.model.enums.FormaPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
@Getter @Setter @NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(unique = true)
    private Agendamento agendamento;

    @NotNull @Positive
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @NotNull
    private FormaPagamento formaPagamento;

    private LocalDateTime dataPagamento;

    public Pagamento(Agendamento agendamento, BigDecimal valor, FormaPagamento formaPagamento) {
        this.agendamento = agendamento;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.dataPagamento = LocalDateTime.now();
    }
}
