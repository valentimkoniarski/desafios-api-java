package com.picpay.picpaydesafio.entities;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transferencias")
@Getter
@Setter
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conta_origem")
    @NotNull
    private String contaOrigem;

    @Column(name = "conta_destino")
    @NotNull
    private String contaDestino;

    @Column(name = "valor")
    @NotNull
    private BigDecimal valor;

    @Column(name = "data_hora")
    @NotNull
    private LocalDateTime dataHora;

    @Column(name = "status")
    private String status;

    @Column(name = "descricao")
    private String descricao;

    @OneToOne
    @JoinColumn(name = "usuario_transferidor_id")
    private Usuario usuarioTransferidor;

    @OneToOne
    @JoinColumn(name = "usuario_recebedor_id")
    private Usuario usuarioRecebedor;

}
