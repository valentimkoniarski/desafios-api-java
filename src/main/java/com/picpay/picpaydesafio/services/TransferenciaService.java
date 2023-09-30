package com.picpay.picpaydesafio.services;

import com.picpay.picpaydesafio.dtos.TransferenciaDto;
import com.picpay.picpaydesafio.dtos.UsuarioDto;
import com.picpay.picpaydesafio.entities.Transferencia;
import com.picpay.picpaydesafio.entities.Usuario;
import com.picpay.picpaydesafio.repositories.LojistaRepository;
import com.picpay.picpaydesafio.repositories.TransferenciaRepository;
import com.picpay.picpaydesafio.validacoes.transferencia.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransferenciaService implements TransferenciaSaldo {

    private final TransferenciaRepository transferenciaRepository;

    private final LojistaRepository lojistaRepository;

    private final UsuarioService usuarioService;

    private final ModelMapper modelMapper;

    private final List<ValidacaoTransferencia> validacaoTransferencias;

    private final SaldoAtualizacao saldoAtualizacao;

    @Autowired
    public TransferenciaService(TransferenciaRepository transferenciaRepository,
                                LojistaRepository lojistaRepository,
                                UsuarioService usuarioService,
                                ModelMapper modelMapper,
                                SaldoAtualizacao saldoAtualizacao) {
        this.transferenciaRepository = transferenciaRepository;
        this.lojistaRepository = lojistaRepository;
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
        this.validacaoTransferencias = validacoes();
        this.saldoAtualizacao = saldoAtualizacao;
    }

    /**
     * Realiza uma transferência entre clientes, atualizando os saldos dos clientes
     * e fazendo as validações necessárias que se encontram no método `setValidacaoDeCricao()`.
     *
     * @param request          HttpServletRequest
     * @param transferenciaDto DTO contendo os dados da transferência.
     */
    public void transferencia(HttpServletRequest request, TransferenciaDto transferenciaDto) {
        Transferencia transferencia = setterTransferenciaDto(request, transferenciaDto);
        atualizarSaldos(transferencia);
        validacoes(transferencia);
        transferenciaRepository.save(transferencia);
    }

    private Transferencia setterTransferenciaDto(HttpServletRequest request, TransferenciaDto transferenciaDto) {
        Usuario usuarioTransferidor = usuarioService.getUserByToken(request);
        Usuario usuarioRecebedor = usuarioService.getUsuarioById(transferenciaDto.getUsuarioRecebedor().getId());

        TransferenciaDto transferenciaCompletaDto = new TransferenciaDto();
        transferenciaCompletaDto.setUsuarioRecebedor(modelMapper.map(usuarioRecebedor, UsuarioDto.class));
        transferenciaCompletaDto.setContaDestino(usuarioRecebedor.getEmail());

        transferenciaCompletaDto.setUsuarioTransferidor(modelMapper.map(usuarioTransferidor, UsuarioDto.class));
        transferenciaCompletaDto.setContaOrigem(usuarioTransferidor.getEmail());

        transferenciaCompletaDto.setValor(transferenciaDto.getValor());
        transferenciaCompletaDto.setDataHora(LocalDateTime.now());
        transferenciaCompletaDto.setDescricao(transferenciaDto.getDescricao());
        transferenciaCompletaDto.setStatus("Enviado");

        Transferencia transferencia = modelMapper.map(transferenciaCompletaDto, Transferencia.class);

        return transferencia;
    }

    private List<ValidacaoTransferencia> validacoes() {
        List<ValidacaoTransferencia> validacoes = new ArrayList<>();
        validacoes.add(new LojistaNaoTransfere(lojistaRepository));
        validacoes.add(new SaldoNaoPodeSerNegativo());
        return validacoes;
    }

    private void validacoes(Transferencia transferencia) {
        validacaoTransferencias.forEach(validacao -> validacao.validacao(transferencia));
    }

    @Override
    public void atualizarSaldos(Transferencia transferencia) {
        saldoAtualizacao.atualizarSaldoRecebedor(transferencia.getValor(), transferencia.getUsuarioRecebedor().getId());
        saldoAtualizacao.atualizarSaldoTransferidor(transferencia.getValor(), transferencia.getUsuarioTransferidor().getId());
    }
}
