package com.picpay.picpaydesafio.controllers;

import com.picpay.picpaydesafio.dtos.TransferenciaDto;
import com.picpay.picpaydesafio.exceptions.TransferenciaValidacoesException;
import com.picpay.picpaydesafio.services.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/transferencias")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    private final HttpServletRequest request;

    @Autowired
    public TransferenciaController(TransferenciaService transferenciaService, HttpServletRequest request) {
        this.transferenciaService = transferenciaService;
        this.request = request;
    }

    @PostMapping
    public ResponseEntity transferenciaEntreClientes(@Valid @RequestBody TransferenciaDto transferenciaDto) {
        try {
            transferenciaService.transferencia(request, transferenciaDto);
            return ResponseEntity.ok().build();
        } catch (TransferenciaValidacoesException.LojistaNaoPodeTransferirException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (TransferenciaValidacoesException.SaldoNaoPodeSerNegativoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
