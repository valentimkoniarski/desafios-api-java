package com.picpay.picpaydesafio.validacoes.transferencia;

import com.picpay.picpaydesafio.entities.Transferencia;

import java.math.BigDecimal;

public class SaldoNaoPodeSerNegativo implements ValidacaoTransferencia {

    @Override
    public void validacao(Transferencia transferencia) {
        if (transferencia.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Saldo não pode ser negativo");
        }
    }
}
