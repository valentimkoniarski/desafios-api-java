package com.picpay.picpaydesafio.controllers;

import com.picpay.picpaydesafio.dtos.TransferenciaDto;
import com.picpay.picpaydesafio.exceptions.TransferenciaValidacoesException;
import com.picpay.picpaydesafio.services.TransferenciaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/transferencias")
@AllArgsConstructor
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @PostMapping
    public ResponseEntity transferenciaEntreClientes(HttpServletRequest request,
                                                     @RequestBody TransferenciaDto transferenciaDto) {
        try {
            transferenciaService.transferencia(request, transferenciaDto);
            return ResponseEntity.ok().build();
        } catch (TransferenciaValidacoesException.LojistaNaoPodeTransferirException
                 | TransferenciaValidacoesException.SaldoNaoPodeSerNegativoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
