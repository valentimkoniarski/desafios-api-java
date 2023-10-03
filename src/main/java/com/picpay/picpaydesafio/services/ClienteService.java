package com.picpay.picpaydesafio.services;

import com.picpay.picpaydesafio.dtos.ClienteDto;
import com.picpay.picpaydesafio.entities.Cliente;
import com.picpay.picpaydesafio.entities.Usuario;
import com.picpay.picpaydesafio.repositories.ClienteRepository;
import com.picpay.picpaydesafio.validacoes.cliente.SaldoInicialMil;
import com.picpay.picpaydesafio.validacoes.cliente.SomenteUmClientePorUsuario;
import com.picpay.picpaydesafio.validacoes.cliente.ValidacaoCriacaoDoCliente;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class ClienteService {

    private final HttpServletRequest request;

    private final ModelMapper modelMapper;

    private final ClienteRepository clienteRepository;

    private final List<ValidacaoCriacaoDoCliente> validacoes;

    public ClienteService(final ModelMapper modelMapper,
                          final ClienteRepository clienteRepository,
                          final HttpServletRequest request) {
        this.modelMapper = modelMapper;
        this.clienteRepository = clienteRepository;
        this.request = request;
        this.validacoes = setValidacoes();
    }

    public void salvar() {
        final long saldoInicial = 1000L;
        final ClienteDto clienteDto = new ClienteDto(BigDecimal.valueOf(saldoInicial));
        final Cliente cliente = modelMapper.map(clienteDto, Cliente.class);
        setterUsuario(cliente);
        validacoes(cliente);
        clienteRepository.save(cliente);
    }

    private void setterUsuario(Cliente cliente) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        cliente.setUsuario(usuario);
    }

    private List<ValidacaoCriacaoDoCliente> setValidacoes() {
        List<ValidacaoCriacaoDoCliente> listaDeValidacoes = new ArrayList<>();
        listaDeValidacoes.add(new SaldoInicialMil());
        listaDeValidacoes.add(new SomenteUmClientePorUsuario(clienteRepository));
        return listaDeValidacoes;
    }

    private void validacoes(Cliente cliente) {
        validacoes.forEach(validacao -> {
            validacao.validacao(cliente);
        });
    }
}
