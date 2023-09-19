package com.picpay.picpaydesafio.services;

import com.picpay.picpaydesafio.dtos.ClienteDto;
import com.picpay.picpaydesafio.entities.Cliente;
import com.picpay.picpaydesafio.entities.Usuario;
import com.picpay.picpaydesafio.repositories.ClienteRepository;
import com.picpay.picpaydesafio.validacoes.cliente.SaldoInicialZero;
import com.picpay.picpaydesafio.validacoes.cliente.SomenteUmClientePorUsuario;
import com.picpay.picpaydesafio.validacoes.cliente.ValidacaoCriacaoDoCliente;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ClienteService {

    private final ModelMapper modelMapper;

    private final ClienteRepository clienteRepository;

    private final UsuarioService usuarioService;

    private final List<ValidacaoCriacaoDoCliente> validacoes;

    public ClienteService(final ModelMapper modelMapper,
                          final ClienteRepository clienteRepository,
                          final UsuarioService usuarioService,
                          final List<ValidacaoCriacaoDoCliente> validacoes) {
        this.modelMapper = modelMapper;
        this.clienteRepository = clienteRepository;
        this.usuarioService = usuarioService;
        this.validacoes = setValidacoesDeCricao();
    }

    public void salvar(HttpServletRequest request) {
        final ClienteDto clienteDto = new ClienteDto(BigDecimal.ZERO);
        final Cliente cliente = modelMapper.map(clienteDto, Cliente.class);
        setterUsuario(request, cliente);
        validacoes(cliente);
        clienteRepository.save(cliente);
    }

    private void setterUsuario(HttpServletRequest request, Cliente cliente) {
        Usuario usuario = usuarioService.getUserByToken(request);
        cliente.setUsuario(usuario);
    }

    private List<ValidacaoCriacaoDoCliente> setValidacoesDeCricao() {
        List<ValidacaoCriacaoDoCliente> validacoes = new ArrayList<>();
        validacoes.add(new SaldoInicialZero());
        validacoes.add(new SomenteUmClientePorUsuario(clienteRepository));
        return validacoes;
    }

    private void validacoes(Cliente cliente) {
        validacoes.forEach(validacao -> validacao.validacao(cliente));
    }
}
