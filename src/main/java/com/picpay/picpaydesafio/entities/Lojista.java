package com.picpay.picpaydesafio.entities;


import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "lojistas")
@Setter
@Getter
public class Lojista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal saldo;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
