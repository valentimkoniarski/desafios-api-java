package com.picpay.picpaydesafio.validacoes.cliente;

import com.picpay.picpaydesafio.entities.Cliente;
import com.picpay.picpaydesafio.exceptions.ClienteValidacoesException;

import java.math.BigDecimal;

public class SaldoInicialZero implements ValidacaoCriacaoDoCliente {
    @Override
    public void validacao(Cliente cliente) {
        if (cliente.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            throw new ClienteValidacoesException.SaldoInicialInvalidoException();
        }
    }
}
