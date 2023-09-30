package com.picpay.picpaydesafio.validacoes.transferencia;

import com.picpay.picpaydesafio.repositories.LojistaRepository;
import com.picpay.picpaydesafio.repositories.TransferenciaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SaldoAtualizacaoImpl implements SaldoAtualizacao {

    private final TransferenciaRepository transferenciaRepository;
    private final LojistaRepository lojistaRepository;

    public SaldoAtualizacaoImpl(TransferenciaRepository transferenciaRepository,
                                LojistaRepository lojistaRepository) {
        this.transferenciaRepository = transferenciaRepository;
        this.lojistaRepository = lojistaRepository;
    }

    @Override
    public void atualizarSaldoRecebedor(BigDecimal valor, Long usuarioId) {
        boolean isLojista = lojistaRepository.existsByUsuario(usuarioId);

        if (isLojista) {
            transferenciaRepository.updateSaldoLojistaRecebedor(valor, usuarioId);
        } else {
            transferenciaRepository.updateSaldoClienteRecebedor(valor, usuarioId);
        }
    }

    @Override
    public void atualizarSaldoTransferidor(BigDecimal valor, Long usuarioId) {
        transferenciaRepository.updateSaldoClienteTransferidor(valor, usuarioId);
    }
}
