package com.picpay.picpaydesafio.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.picpay.picpaydesafio.exceptions.ClienteValidacoesException;
import com.picpay.picpaydesafio.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarCliente() {
        try {
            clienteService.salvar();
            return ResponseEntity.ok().build();
        } catch (ClienteValidacoesException.ClienteExistenteException |
                 ClienteValidacoesException.SaldoInicialInvalidoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
