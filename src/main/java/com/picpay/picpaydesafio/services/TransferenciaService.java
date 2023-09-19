package com.picpay.picpaydesafio.services;

import com.picpay.picpaydesafio.dtos.TransferenciaDto;
import com.picpay.picpaydesafio.dtos.UsuarioDto;
import com.picpay.picpaydesafio.entities.Transferencia;
import com.picpay.picpaydesafio.entities.Usuario;
import com.picpay.picpaydesafio.repositories.ClienteRepository;
import com.picpay.picpaydesafio.repositories.LojistaRepository;
import com.picpay.picpaydesafio.repositories.TransferenciaRepository;
import com.picpay.picpaydesafio.validacoes.transferencia.LojistaNaoTransfere;
import com.picpay.picpaydesafio.validacoes.transferencia.SaldoNaoPodeSerNegativo;
import com.picpay.picpaydesafio.validacoes.transferencia.ValidacaoTransferencia;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;

    private final ClienteRepository clienteRepository;

    private final LojistaRepository lojistaRepository;

    private final UsuarioService usuarioService;

    private final ModelMapper modelMapper;

    private final List<ValidacaoTransferencia> validacaoTransferencias;

    public TransferenciaService(TransferenciaRepository transferenciaRepository,
                                ClienteRepository clienteRepository,
                                LojistaRepository lojistaRepository,
                                UsuarioService usuarioService,
                                ModelMapper modelMapper) {
        this.transferenciaRepository = transferenciaRepository;
        this.clienteRepository = clienteRepository;
        this.lojistaRepository = lojistaRepository;
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
        this.validacaoTransferencias = setValidacaoDeCricao();
    }

    /**
     * Realiza uma transferência entre clientes, atualizando os saldos dos clientes
     * e fazendo as validações necessárias que se encontram no método `setValidacaoDeCricao()`.
     *
     * @param request          HttpServletRequest
     * @param transferenciaDto DTO contendo os dados da transferência. Obs.: Os dados do usuario transferidor precisa
     *                         ser obrigatório o ID, o mesmo vale para o usuario recebedor necessita somente do ID.
     */
    public void transferenciaEntreClientes(HttpServletRequest request, TransferenciaDto transferenciaDto) {
        Transferencia transferencia = setterTransferenciaDto(request, transferenciaDto);
        clienteRepository.updateSaldoClienteRecebedor(transferencia.getValor(), transferencia.getUsuarioRecebedor().getId());
        clienteRepository.updateSaldoClienteTransferidor(transferencia.getValor(), transferencia.getUsuarioTransferidor().getId());
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

    private List<ValidacaoTransferencia> setValidacaoDeCricao() {
        List<ValidacaoTransferencia> validacoes = new ArrayList<>();
        validacoes.add(new LojistaNaoTransfere(lojistaRepository));
        validacoes.add(new SaldoNaoPodeSerNegativo());
        return validacoes;
    }

    private void validacoes(Transferencia transferencia) {
        validacaoTransferencias.forEach(validacao -> validacao.validacao(transferencia));
    }

}
