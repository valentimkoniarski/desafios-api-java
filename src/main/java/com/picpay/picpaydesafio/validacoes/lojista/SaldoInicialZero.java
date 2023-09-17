package com.picpay.picpaydesafio.validacoes.lojista;

import com.picpay.picpaydesafio.entities.Lojista;
import com.picpay.picpaydesafio.exceptions.LojistaValidacoesException;

import java.math.BigDecimal;

public class SaldoInicialZero implements ValidacaoCriacaoDoLojista {
    @Override
    public void validacao(Lojista lojista) {
        if (lojista.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            throw new LojistaValidacoesException.SaldoInicialInvalidoException();
        }
    }
}
