package com.picpay.picpaydesafio.controllers;


import com.picpay.picpaydesafio.exceptions.ClienteValidacoesException;
import com.picpay.picpaydesafio.services.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/clientes")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarCliente(HttpServletRequest request) {
        try {
            clienteService.salvar(request);
            return ResponseEntity.ok().build();
        } catch (ClienteValidacoesException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
