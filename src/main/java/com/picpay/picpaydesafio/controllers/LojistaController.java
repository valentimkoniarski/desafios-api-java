package com.picpay.picpaydesafio.controllers;

import com.picpay.picpaydesafio.exceptions.LojistaValidacoesException;
import com.picpay.picpaydesafio.services.LojistaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/lojistas")
@AllArgsConstructor
public class LojistaController {

    private final LojistaService lojistaService;

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarLojista(HttpServletRequest request) {
        try {
            lojistaService.salvar(request);
            return ResponseEntity.ok().build();
        } catch (LojistaValidacoesException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
