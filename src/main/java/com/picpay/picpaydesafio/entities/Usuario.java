package com.picpay.picpaydesafio.entities;


import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    @Column(unique = true)
    private String inscricao;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String senha;

}
