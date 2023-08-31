package com.picpay.picpaydesafio.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
