package com.picpay.picpaydesafio.validacoes.lojista;

import com.picpay.picpaydesafio.entities.Lojista;
import com.picpay.picpaydesafio.exceptions.ClienteValidacoesException;
import com.picpay.picpaydesafio.exceptions.LojistaValidacoesException;
import com.picpay.picpaydesafio.repositories.LojistaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SomenteUmLojistaPorUsuario implements ValidacaoCriacaoDoLojista {

    private final LojistaRepository lojistaRepository;

    @Override
    public void validacao(Lojista lojista) {
        boolean hasLojistaPorUsuario = lojistaRepository.existsByUsuario(lojista.getUsuario().getId());
        if (hasLojistaPorUsuario) {
            throw new LojistaValidacoesException.LojistaExistenteException();
        }
    }
}
