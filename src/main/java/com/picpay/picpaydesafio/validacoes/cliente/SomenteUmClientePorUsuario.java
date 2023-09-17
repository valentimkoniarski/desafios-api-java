package com.picpay.picpaydesafio.validacoes.cliente;

import com.picpay.picpaydesafio.entities.Cliente;
import com.picpay.picpaydesafio.exceptions.ClienteValidacoesException;
import com.picpay.picpaydesafio.repositories.ClienteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SomenteUmClientePorUsuario implements ValidacaoCriacaoDoCliente {

    private final ClienteRepository clienteRepository;

    @Override
    public void validacao(Cliente cliente) {
        boolean hasClientePorUsuario = clienteRepository.findClienteByUsuarioId(cliente.getUsuario().getId());
        if (hasClientePorUsuario) {
            throw new ClienteValidacoesException.ClienteExistenteException();
        }
    }
}
