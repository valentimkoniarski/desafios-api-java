package com.picpay.picpaydesafio.validacoes.transferencia;

import com.picpay.picpaydesafio.entities.Transferencia;
import com.picpay.picpaydesafio.entities.Usuario;
import com.picpay.picpaydesafio.repositories.LojistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LojistaNaoTransfere implements ValidacaoTransferencia {

    private final LojistaRepository lojistaRepository;

    @Autowired
    public LojistaNaoTransfere(LojistaRepository lojistaRepository) {
        this.lojistaRepository = lojistaRepository;
    }

    @Override
    public void validacao(Transferencia transferencia) {
        Usuario usuarioTransferidor = transferencia.getUsuarioTransferidor();

        if (isLojista(usuarioTransferidor)) {
            throw new IllegalArgumentException("Lojista não pode realizar transferência");
        }
    }

    private boolean isLojista(Usuario usuario) {
        return lojistaRepository.existsByUsuario(usuario.getId());
    }
}
