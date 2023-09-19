package com.picpay.picpaydesafio.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransferenciaDto {

    @NotNull
    private String contaOrigem;

    @NotNull
    private String contaDestino;

    @NotNull
    private BigDecimal valor;

    @NotNull
    private LocalDateTime dataHora;

    @NotEmpty
    private String status;

    private String descricao;

    @NotNull
    private UsuarioDto usuarioTransferidor;

    @NotNull
    private UsuarioDto usuarioRecebedor;
}
