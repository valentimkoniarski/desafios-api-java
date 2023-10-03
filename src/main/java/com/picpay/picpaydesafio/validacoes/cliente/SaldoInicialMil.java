package com.picpay.picpaydesafio.validacoes.cliente;

import com.picpay.picpaydesafio.entities.Cliente;
import com.picpay.picpaydesafio.exceptions.ClienteValidacoesException;

import java.math.BigDecimal;

public class SaldoInicialMil implements ValidacaoCriacaoDoCliente {
    @Override
    public void validacao(Cliente cliente) {
        if (!isIgualAMil(cliente)) {
            throw new ClienteValidacoesException.SaldoInicialInvalidoException();
        }
    }

    private static boolean isIgualAMil(Cliente cliente) {
        final BigDecimal saldoInicialMil = BigDecimal.valueOf(1000);
        return cliente.getSaldo().equals(saldoInicialMil);
    }
}
