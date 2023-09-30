package com.picpay.picpaydesafio.validacoes.transferencia;

import java.math.BigDecimal;

public interface SaldoAtualizacao {
    void atualizarSaldoRecebedor(BigDecimal valor, Long usuarioId);
    void atualizarSaldoTransferidor(BigDecimal valor, Long usuarioId);
}

