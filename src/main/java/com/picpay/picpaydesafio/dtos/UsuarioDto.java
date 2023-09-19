package com.picpay.picpaydesafio.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {

    private Long id;
    private String nome;
    private String inscricao;
    private String email;
    private String senha;
}
