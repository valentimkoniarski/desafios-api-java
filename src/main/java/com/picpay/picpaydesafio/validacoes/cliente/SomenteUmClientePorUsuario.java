package com.picpay.picpaydesafio.validacoes.cliente;

import com.picpay.picpaydesafio.entities.Cliente;
import com.picpay.picpaydesafio.exceptions.ClienteValidacoesException;
import com.picpay.picpaydesafio.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SomenteUmClientePorUsuario implements ValidacaoCriacaoDoCliente {

    private final ClienteRepository clienteRepository;

    @Autowired
    public SomenteUmClientePorUsuario(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void validacao(Cliente cliente) {
        boolean hasClientePorUsuario = clienteRepository.findClienteByUsuarioId(cliente.getUsuario().getId());
        if (hasClientePorUsuario) {
            throw new ClienteValidacoesException.ClienteExistenteException();
        }
    }
}
